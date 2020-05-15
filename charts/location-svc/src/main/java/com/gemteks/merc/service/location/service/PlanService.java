package com.gemteks.merc.service.location.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cloudant.client.api.views.ViewResponse;
import com.cloudant.client.api.views.ViewResponse.Row;
import com.gemteks.merc.service.location.common.MercException;
import com.gemteks.merc.service.location.common.MercValidator;
import com.gemteks.merc.service.location.model.ActionInfo;
import com.gemteks.merc.service.location.repo.AmMicroService;
import com.gemteks.merc.service.location.repo.CloudantDB;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

@Configuration
@PropertySource("classpath:application.properties")
public class PlanService {

    private String docName = "iotPlan";
    private String viewName = "by-cpId";

    @Autowired
    private CloudantDB cloudantDBInstance;

    public JsonArray getPlanInfo(String token) throws MercException {

        MercValidator.validateParam(token);
        ActionInfo actionInfo = AmMicroService.getDataFromAmServiceWithToken(token);
        List<Row<String, JsonObject>> docList = cloudantDBInstance.getDocsUsingViewByKey(this.docName, this.viewName, actionInfo.getCpID());

        JsonArray docJsonArray = new JsonArray();


        if (docList == null) {
            System.out.println("docList is null: no document been found ");
            //Create view for searching document
            JsonObject jobj = new JsonObject();
            JsonObject viewObj = new JsonObject();
            JsonObject mapObj = new JsonObject();
            mapObj.addProperty("map", "function (doc) {\n  if(doc.cpId){\n    emit(doc.cpId, doc);  \n  }\n}");
            viewObj.add(this.viewName, mapObj);

            jobj.addProperty("_id", String.format("_design/%s", this.docName));
            jobj.add("views", viewObj);
            jobj.addProperty("language", "javascript");
            if (cloudantDBInstance.putDocs(jobj) == false) {
                throw new MercException("999", "create view error");
            }
            //Get document by cpId
            docList = cloudantDBInstance.getDocsUsingViewByKey(this.docName, "by-cpId", actionInfo.getCpID());        

        }

        if (docList.size() == 0) {
            throw new MercException("404", "no exist data");
        }

        for (ViewResponse.Row<String, JsonObject> row : docList){

            JsonObject doc = new JsonObject();
            JsonObject vObject = row.getValue();
            doc.addProperty("id", row.getId());
            doc.addProperty("name", vObject.get("name") == null?"NA":vObject.get("name").getAsString());
            doc.addProperty("desc", vObject.get("desc") == null?"NA":vObject.get("desc").getAsString());
            doc.addProperty("version", vObject.get("version") == null?"NA":vObject.get("version").getAsString());
            if (vObject.get("verifier") != null)
                doc.addProperty("verifier",vObject.get("verifier").getAsString());

            JsonObject attachment = vObject.get("_attachments").getAsJsonObject();

            for (String key: attachment.keySet()) {
                doc.addProperty("fileName", key);
                JsonObject fileInfo = attachment.get(key).getAsJsonObject();
                doc.addProperty("content_type",  fileInfo.get("content_type").getAsString());
                doc.add("length",  fileInfo.get("length"));

            }
            
            docJsonArray.add(doc);
        
         }
        System.out.printf("docList size:%d\n", docList.size());
       
        return docJsonArray;
    }

    public Boolean deletePlanInfo(String id, String token) throws MercException {

        MercValidator.validateParam(token);
        MercValidator.validateParam(id);

        AmMicroService.getDataFromAmServiceWithToken(token);

        List<JsonObject> docs = cloudantDBInstance.getDocsById(id);  
        
        if (docs.size() == 0) {
            throw new MercException("404", "No exist data");
        }
        JsonObject doc = docs.get(0);

        return cloudantDBInstance.removeDocs(doc);
    }

    public Integer insertOrUpdatePlanInfo (MultipartFile  file, HttpServletRequest request) throws MercException {
        String token = request.getParameter("token");
        String id = request.getParameter("id");
        ActionInfo actionInfo = AmMicroService.getDataFromAmServiceWithToken(token);
        JsonObject doc = new JsonObject();
        Integer putDocStatus = 1;

        MercValidator.validateParam(request.getParameter("id"));
        MercValidator.validateParam(request.getParameter("name"));
        MercValidator.validateParam(request.getParameter("version"));
        MercValidator.validateParam(request.getParameter("desc"));
        MercValidator.validateParam(request.getParameter("token"));

        doc.addProperty("name", request.getParameter("name"));
        doc.addProperty("version", request.getParameter("version"));
        doc.addProperty("desc", request.getParameter("desc"));
        doc.addProperty("cpId", actionInfo.getCpID());
        doc.addProperty("userId", actionInfo.getUserID());

        if (request.getParameter("verifier") !=null ) {
            doc.addProperty("verifier", request.getParameter("verifier"));
        }

        if (!file.isEmpty()) {
            try {
                String imageBase64Content = Base64.getEncoder().encodeToString(file.getBytes());

                JsonObject dataContent = new JsonObject();
                dataContent.addProperty("content_type", file.getContentType());
                dataContent.addProperty("data", imageBase64Content);

                JsonObject fileAttachment = new JsonObject();
                fileAttachment.add(file.getOriginalFilename(), dataContent);
                doc.add("_attachments", fileAttachment);
            } catch (IOException e){

            }
            
        }

        //Create document
        if (id.equals("-1")) {
            if (cloudantDBInstance.putDocs(doc) == false) {
                throw new MercException("999", "Insert plan info fail");
            }
        } else {
            List<JsonObject> docs = cloudantDBInstance.getDocsById(id);
            if (docs.size() == 0) {
                throw new MercException("404", "No exist data");
            }
            JsonObject existedDoc = docs.get(0);

            //Update document
            doc.addProperty("_id", existedDoc.get("_id").getAsString());
            doc.addProperty("_rev", existedDoc.get("_rev").getAsString());

            if (cloudantDBInstance.updateDocs(doc) == false) {
                throw new MercException("999", "Update plan info fail");
            }
            putDocStatus = 2;
        }

        return putDocStatus;
    }

    public byte[] getPlanImage (String token, String id) throws MercException {
        InputStream  attachmentStream = null;
        byte[] imageRawData = "".getBytes();

        MercValidator.validateParam(id);
        MercValidator.validateParam(token);
        AmMicroService.getDataFromAmServiceWithToken(token);
        
        List<JsonObject> docs = cloudantDBInstance.getDocsById(id);
        if (docs.size() == 0) {
            throw new MercException("404", "No exist data");
        }
        JsonObject doc = docs.get(0);
        JsonObject attachments = doc.get("_attachments").getAsJsonObject();

        for (String attachment: attachments.keySet()) {
            attachmentStream = cloudantDBInstance.getAttachment(id, attachment);
            break;
        }
        try {
            if (attachmentStream != null) {
                imageRawData =  IOUtils.toByteArray(attachmentStream);
            }
        } catch (IOException e){
            throw new MercException("999", "Get attatchment fail");
        }

        return imageRawData;
    }
}