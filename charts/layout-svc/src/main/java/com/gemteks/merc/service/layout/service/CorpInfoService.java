package com.gemteks.merc.service.layout.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cloudant.client.api.views.ViewResponse;
import com.cloudant.client.api.views.ViewResponse.Row;
import com.gemteks.merc.service.layout.common.MERCUtils;
import com.gemteks.merc.service.layout.common.MERCValidator;
import com.gemteks.merc.service.layout.common.MercException;
import com.gemteks.merc.service.layout.model.ActionInfo;
import com.gemteks.merc.service.layout.repo.AmMicroService;
import com.gemteks.merc.service.layout.repo.cloudant.CloudantDB;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Scope(value="prototype")
public class CorpInfoService{

    private String dbName = "cp_info";
    private String docName = "iotPlan";
    private String viewName = "by-cpId";

    /*@Autowired
    private MERCUtils mercUtils;*/

    @Autowired
    private CloudantDB cloudantDBInstance;

    public Integer insertOrUpdateCorpInfo(MultipartFile  file, HttpServletRequest request) throws MercException {
        String id = request.getParameter("id");
        String token = request.getParameter("token");
        JsonObject doc = new JsonObject();
        Integer putDocStatus = 1;

        MERCValidator.validateParam(id);
        MERCValidator.validateParam(token);
        MERCValidator.validateParam(request.getParameter("cpName"));
        MERCValidator.validateParam(request.getParameter("name_en"));
        MERCValidator.validateParam(request.getParameter("name_zh"));
        MERCValidator.validateParam(request.getParameter("name_zh-Hans"));
        MERCValidator.validateParam(request.getParameter("url"));
        MERCValidator.validateParam(request.getParameter("welcome_en"));
        MERCValidator.validateParam(request.getParameter("welcome_zh"));
        MERCValidator.validateParam(request.getParameter("welcome_zh-Hans"));
        MERCValidator.validateParam(request.getParameter("title_en"));
        MERCValidator.validateParam(request.getParameter("title_zh"));
        MERCValidator.validateParam(request.getParameter("title_zh-Hans"));
        MERCValidator.validateParam(request.getParameter("isEnable"));

        doc.addProperty("cpName", request.getParameter("cpName"));
        doc.addProperty("name_en", request.getParameter("name_en"));
        doc.addProperty("name_zh", request.getParameter("name_zh"));
        doc.addProperty("name_zh-Hans", request.getParameter("name_zh-Hans"));
        doc.addProperty("url", request.getParameter("url"));
        doc.addProperty("welcome_en", request.getParameter("welcome_en"));
        doc.addProperty("welcome_zh", request.getParameter("welcome_zh"));
        doc.addProperty("welcome_zh-Hans", request.getParameter("welcome_zh-Hans"));
        doc.addProperty("title_en", request.getParameter("title_en"));
        doc.addProperty("title_zn", request.getParameter("title_zn"));
        doc.addProperty("title_zh-Hans", request.getParameter("title_zh-Hans"));
        doc.addProperty("isEnable", request.getParameter("isEnable"));
        doc.addProperty("token", token);


       // ActionInfo actionInfo = mercUtils.checkValidToken(token);
        ActionInfo actionInfo =  AmMicroService.getDataFromAmServiceWithToken(token);


        doc.addProperty("cpId", actionInfo.getCpID());
        doc.addProperty("userId", actionInfo.getUserID());

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

        //Create document
        if (id.equals("-1")) {
            if (cloudantDBInstance.putDocs(dbName, doc) == false) {
                throw new MercException("999", "Insert company info fail");
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
                throw new MercException("999", "Update company info fail");
            }
            putDocStatus = 2;
        }

        return putDocStatus;
    }

    public byte[] getLogoImage(String id) throws MercException {

        MERCValidator.validateParam(id);

        byte[] logoRawImage = cloudantDBInstance.getImage(dbName, id);

        if (logoRawImage == null) {
            new MercException("999", "Get attatchment fail");
        }

        Integer imageSize = logoRawImage.length;

        if (imageSize == 0) {
            throw new MercException("404", "No exist data");
        }  

        return logoRawImage;
    }

    public byte[] getLogoImageFromToken(String token) throws MercException {

        MERCValidator.validateParam(token);
       // ActionInfo actionInfo = mercUtils.checkValidToken(token);
        ActionInfo actionInfo =  AmMicroService.getDataFromAmServiceWithToken(token);

        cloudantDBInstance.setCloudantDBName(dbName);
        List<Row<String, JsonObject>> docList = cloudantDBInstance.getDocsUsingViewByKey(dbName, docName, viewName, actionInfo.getCpID());

        if (docList.size() == 0) {
            throw new MercException("404", "no exist data");
        }

        ViewResponse.Row<String, JsonObject> cpInfoObject = docList.get(0);


        byte[] logoRawImage = cloudantDBInstance.getImage(dbName, cpInfoObject.getId());

        if (logoRawImage == null) {
            new MercException("999", "Get attatchment fail");
        }

        Integer imageSize = logoRawImage.length;

        if (imageSize == 0) {
            throw new MercException("404", "No exist data");
        } 

        return logoRawImage;
    }

    public JsonObject getCpInfo(Map<String, String> queryMap) throws MercException {

        String locale = queryMap.get("locale");
        String token = queryMap.get("token");
        String cpId = "1";
        if(token != null && token.length() > 0 ){
            ActionInfo actionInfo =  AmMicroService.getDataFromAmServiceWithToken(token);
            cpId = actionInfo.getCpID();
        }
        cloudantDBInstance.setCloudantDBName(dbName);
        List<Row<String, JsonObject>> docList = cloudantDBInstance.getDocsUsingViewByKey(dbName, this.docName, this.viewName, cpId);

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
            if (cloudantDBInstance.putDocs(dbName, jobj) == false) {
                throw new MercException("999", "create view error");
            }
            //Get document by cpId
            docList = cloudantDBInstance.getDocsUsingViewByKey(dbName, this.docName, viewName, "1");
        }

        if (docList.size() == 0) {
            throw new MercException("404", "no exist data");
        }

        JsonObject vObject = docList.get(0).getValue();

        JsonObject doc = addCpInfoProperty(vObject, locale);

        System.out.printf("docList size:%d\n", docList.size());

        return doc;
    }

    public JsonObject getCpInfoById(Map<String, String> queryMap) throws MercException {
        String token = queryMap.get("token");
        String locale = queryMap.get("locale");
        String id = queryMap.get("id");

        MERCValidator.validateParam(token);
        //mercUtils.checkValidToken(token);
        AmMicroService.getDataFromAmServiceWithToken(token);

        List<JsonObject> docList = cloudantDBInstance.getDocsById(dbName, id);

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
            if (cloudantDBInstance.putDocs(dbName, jobj) == false) {
                throw new MercException("999", "create view error");
            }
            //Get document by cpId
            docList = cloudantDBInstance.getDocsById(dbName, id);
        }

        if (docList.size() == 0) {
            throw new MercException("404", "no exist data");
        }

        JsonObject vObject = docList.get(0);

        JsonObject doc = addCpInfoProperty(vObject, locale);

        System.out.printf("docList size:%d\n", docList.size());

        return doc;
    }

    public JsonObject addCpInfoProperty(JsonObject vObject, String locale){
        JsonObject docTmp = new JsonObject();

        if (locale == null || locale.equals("")){
            docTmp.addProperty("title_en", vObject.get("title_en") == null?"NA":vObject.get("title_en").getAsString());
            docTmp.addProperty("title_zh", vObject.get("title_zh") == null?"NA":vObject.get("title_zh").getAsString());
            docTmp.addProperty("title_zh-Hans", vObject.get("title_zh-Hans") == null?"NA":vObject.get("title_zh-Hans").getAsString());
            docTmp.addProperty("welcome_en", vObject.get("welcome_en") == null?"NA":vObject.get("welcome_en").getAsString());
            docTmp.addProperty("welcome_zh", vObject.get("welcome_zh") == null?"NA":vObject.get("welcome_zh").getAsString());
            docTmp.addProperty("welcome_zh-Hans", vObject.get("welcome_zh-Hans") == null?"NA":vObject.get("welcome_zh-Hans").getAsString());
            docTmp.addProperty("name_en", vObject.get("name_en") == null?"NA":vObject.get("name_en").getAsString());
            docTmp.addProperty("name_zh", vObject.get("name_zh") == null?"NA":vObject.get("name_zh").getAsString());
            docTmp.addProperty("name_zh-Hans", vObject.get("name_zh-Hans") == null?"NA":vObject.get("name_zh-Hans").getAsString());
            docTmp.addProperty("id", vObject.get("_id") == null?"NA":vObject.get("_id").getAsString());
            docTmp.addProperty("isEnable", vObject.get("isEnable") == null?"NA":vObject.get("isEnable").getAsString());
            docTmp.addProperty("cpName", vObject.get("cpName") == null?"NA":vObject.get("cpName").getAsString());
            docTmp.addProperty("url", vObject.get("url") == null?"NA":vObject.get("url").getAsString());
        }
        else {
            String tileKey = "title_".concat(locale);
            String welcomeKey = "welcome_".concat(locale);
            String nameKey = "name_".concat(locale);

            docTmp.addProperty("title", vObject.get(tileKey) == null?"NA":vObject.get(tileKey).getAsString());
            docTmp.addProperty("welcome", vObject.get(welcomeKey) == null?"NA":vObject.get(welcomeKey).getAsString());
            docTmp.addProperty("name", vObject.get(nameKey) == null?"NA":vObject.get(nameKey).getAsString());
            docTmp.addProperty("id", vObject.get("_id") == null?"NA":vObject.get("_id").getAsString());
            docTmp.addProperty("isEnable", vObject.get("isEnable") == null?"NA":vObject.get("isEnable").getAsString());
            docTmp.addProperty("cpName", vObject.get("cpName") == null?"NA":vObject.get("cpName").getAsString());
            docTmp.addProperty("url", vObject.get("url") == null?"NA":vObject.get("url").getAsString());
        }

        return docTmp;
    }

}