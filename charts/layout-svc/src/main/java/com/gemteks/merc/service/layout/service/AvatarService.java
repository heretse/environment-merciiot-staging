package com.gemteks.merc.service.layout.service;

import java.io.IOException;
import java.util.*;

import com.cloudant.client.api.views.ViewResponse;
import com.cloudant.client.api.views.ViewResponse.Row;
import com.gemteks.merc.service.layout.common.MERCValidator;
import com.gemteks.merc.service.layout.common.MercException;
import com.gemteks.merc.service.layout.model.ActionInfo;
import com.gemteks.merc.service.layout.repo.AmMicroService;
import com.gemteks.merc.service.layout.repo.cloudant.CloudantDB;
import com.gemteks.merc.service.layout.repo.mysql.ApiUserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public class AvatarService{
    private String dbName = "avatar_info";
    private String docName = "iotPlan";
    private String userIdViewName = "by-userId";

    @Autowired
    private CloudantDB cloudantDBInstance;

    @Autowired
    private ApiUserRepository apiUserRepo;

    public byte[] getAvatarImage(String token, String id) throws MercException {

        MERCValidator.validateParam(token);
        MERCValidator.validateParam(id);

        AmMicroService.getDataFromAmServiceWithToken(token);

        byte[] rawImage = cloudantDBInstance.getImage(dbName, id);

        if (rawImage == null) {
            new MercException("999", "Get attatchment fail");
        }

        Integer imageSize = rawImage.length;

        if (imageSize == 0) {
            throw new MercException("404", "No exist data");
        }  

        return rawImage;
        
    }

    public JsonArray getAvatarInfo(String token) throws MercException {

        MERCValidator.validateParam(token);
        ActionInfo actionInfo = AmMicroService.getDataFromAmServiceWithToken(token);

        // get doc by UserId
        List<Row<String, JsonObject>> docList = cloudantDBInstance.getDocsUsingViewByKey(dbName, this.docName, this.userIdViewName, actionInfo.getUserID());

        JsonArray docJsonArray = new JsonArray();

        if (docList == null) {
            System.out.println("docList is null: no document been found ");
            //Create view for searching document
            JsonObject jobj = new JsonObject();
            JsonObject viewObj = new JsonObject();
            JsonObject mapObj = new JsonObject();
            mapObj.addProperty("map", "function (doc) {\\n  if(doc.userId){\\n    emit(doc.userId, doc);  \\n  }\\n}");
            viewObj.add(this.userIdViewName, mapObj);

            jobj.addProperty("_id", String.format("_design/%s", this.docName));
            jobj.add("views", viewObj);
            jobj.addProperty("language", "javascript");
            if (cloudantDBInstance.putDocs(dbName, jobj) == false) {
                throw new MercException("999", "create view error");
            }
            //Get document by userId
            docList = cloudantDBInstance.getDocsUsingViewByKey(dbName, this.docName, this.userIdViewName, actionInfo.getUserID());

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
            doc.addProperty("verifier", vObject.get("verifier") == null?"NA":vObject.get("verifier").getAsString());

            JsonObject attachment = vObject.get("_attachments").getAsJsonObject();

            for (String key: attachment.keySet()) {
                JsonObject fileInfo = attachment.get(key).getAsJsonObject();
                doc.addProperty("content_type",  fileInfo.get("content_type").getAsString());
                doc.add("length",  fileInfo.get("length"));
                doc.addProperty("fileName", key);
            }

            docJsonArray.add(doc);

        }
        System.out.printf("docList size:%d\n", docList.size());

        return docJsonArray;
    }

    public Integer insertOrUpdateAvatarInfo (MultipartFile file, HttpServletRequest request) throws MercException {

        String id = request.getParameter("id");
        String token = request.getParameter("token");

        JsonObject doc = new JsonObject();
        Integer putDocStatus = 1;

        // check input is null or not
        MERCValidator.validateParam(id);
        MERCValidator.validateParam(token);
        MERCValidator.validateParam(request.getParameter("name"));
        MERCValidator.validateParam(request.getParameter("desc"));

        // verify the token
        ActionInfo actionInfo = AmMicroService.getDataFromAmServiceWithToken(token);

        doc.addProperty("cpId", actionInfo.getCpID());
        doc.addProperty("userId", actionInfo.getUserID());
        doc.addProperty("name", request.getParameter("name"));
        doc.addProperty("desc", request.getParameter("desc"));

        // check myFile is null or not
        if (file != null && !file.isEmpty()) {
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

        cloudantDBInstance.setCloudantDBName(dbName);

        //Create document
        if (id.equals("-1")) {
            // insert document
            if (cloudantDBInstance.putDocs(dbName, doc) == false) {
                throw new MercException("999", "Insert plan info fail");
            }
        } else {
            List<JsonObject> docs = cloudantDBInstance.getDocsById(dbName, id);
            if (docs.size() == 0) {
                throw new MercException("404", "No exist data");
            }
            JsonObject existedDoc = docs.get(0);

            //Update document
            doc.addProperty("_id", existedDoc.get("_id").getAsString());
            doc.addProperty("_rev", existedDoc.get("_rev").getAsString());

            if (cloudantDBInstance.updateDocs(dbName, doc) == false) {
                throw new MercException("999", "Update plan info fail");
            }
            putDocStatus = 2;
        }

        return putDocStatus;
    }

    public Boolean delAvatar(Map<String, String> param) throws MercException {

        String id = param.get("id");
        String token = param.get("token");
        MERCValidator.validateParam(id);
        MERCValidator.validateParam(token);

        AmMicroService.getDataFromAmServiceWithToken(token);

        List<JsonObject> docList =  cloudantDBInstance.getDocsById(dbName, id);

        if (docList == null || docList.size() == 0) {
            throw new MercException("404", "No exist data");
        }

        return cloudantDBInstance.removeDocs(dbName, docList.get(0));

    }

    public Boolean updateUserAvatar(Map<String, String> param) throws MercException {
        String picId = param.get("id");
        String token = param.get("token");
        MERCValidator.validateParam(picId);
        MERCValidator.validateParam(token);

        ActionInfo tokenData =  AmMicroService.getDataFromAmServiceWithToken(token);

       return (apiUserRepo.updateUser(picId, Integer.valueOf(tokenData.getUserID()), new Date()) == 1)?true:false;

    }

    public Boolean resetAvatar(Map<String, String> param) throws MercException {
        String token = param.get("token");
        String id = param.get("id");
        List<String> delIdList = new ArrayList<>();
        String failMsg = "";
        Integer delDocCnt = 0;

        MERCValidator.validateParam(token);
        MERCValidator.validateParam(id);

        ActionInfo tokenData =  AmMicroService.getDataFromAmServiceWithToken(token);
        
        List<Row<String, JsonObject>> docList = cloudantDBInstance.getDocsUsingViewByKey(dbName, this.docName, 
            this.userIdViewName, tokenData.getUserID());

        if (docList == null || docList.size() == 0){
            throw new MercException("404", "no exist data");
        }

        for (int i=0; i < docList.size(); i++) {
            ViewResponse.Row<String, JsonObject> row = docList.get(i);

            if (!id.equals(row.getId())) {
                delIdList.add(row.getId());
            }
        }

        if (delIdList.size() > 0){
            for (int i=0; i< delIdList.size(); i++) {
                List<JsonObject> docs = cloudantDBInstance.getDocsById(dbName, delIdList.get(i));
                if (docs.size() > 0) {
            
                    if (cloudantDBInstance.removeDocs(dbName, docs.get(0)) == true){
                        delDocCnt++;
                    }else {
                        failMsg += failMsg + "fail at loc:" + String.valueOf(i)+ ",";
                    }
                }                

            }
        }

        if (delIdList.size() > 0 && delDocCnt != delIdList.size()) {
            throw new MercException("500", failMsg);
        }

        return true;
    }

    public String confirmAvatar(Map<String, String> param) throws MercException {

        String token = param.get("token");
        List<String> idList = new ArrayList<>();
        String updatePicId = "";
        String failMsg = "";
        Integer delDocCnt = 0;

        MERCValidator.validateParam(token);
        ActionInfo tokenData =  AmMicroService.getDataFromAmServiceWithToken(token);

        List<Row<String, JsonObject>> docList = cloudantDBInstance.getDocsUsingViewByKey(dbName, this.docName, 
            this.userIdViewName, tokenData.getUserID());

        if (docList == null || docList.size() == 0){
            throw new MercException("404", "no exist data");
        }

        for (int i=0; i < docList.size(); i++) {
            ViewResponse.Row<String, JsonObject> row = docList.get(i);

            if (i < docList.size() - 1){
                
                idList.add(row.getId());
            } else {
                updatePicId = row.getId();
            }

        }
        
        if (idList.size() > 0){
            for (int i=0; i< idList.size(); i++) {
                List<JsonObject> docs = cloudantDBInstance.getDocsById(dbName, idList.get(i));
                if (docs.size() > 0) {
                    if (cloudantDBInstance.removeDocs(dbName, docs.get(0)) == true){
                        delDocCnt++;
                    }else {
                        failMsg += failMsg + "fail at loc:" + String.valueOf(i)+ ",";
                    }
                }                

            }
        }

        if (idList.size() >= 0  && delDocCnt != idList.size()) {
            throw new MercException("500", failMsg);
        }

        return (apiUserRepo.updateUser(updatePicId, Integer.valueOf(tokenData.getUserID()), new Date()) == 1)?updatePicId:"";
    }

}