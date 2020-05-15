package com.gemteks.merc.service.layout.repo.cloudant;

import com.google.gson.JsonObject;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.ViewResponse.Row;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.cloudant.client.api.model.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
@PropertySource("classpath:application.properties")
public class CloudantDB {
    @Value("${cloudant.db}")
    private  String cloudantDBName;

    @Autowired
    private CloudantClient client;

    public void setCloudantDBName(String dbName){
        this.cloudantDBName = dbName;
    }

    public String getCloudantDBName() {
        return cloudantDBName;
    }

    /**
     * Get document by Id from cloudant db
     * @param id as the id of the doucument
     * @return 
     */
    public List<JsonObject> getDocsById(String dbName, String id) {
        Database db = client.database(dbName, false);
        String query = String.format("{\"selector\":{\"_id\": \"%s\"}}", id);
        QueryResult<JsonObject> qrList = db.query(query, JsonObject.class);

        return qrList.getDocs();
    }

    /**
     * Insert document to cloudant db
     * @param docs as Json type document
     * @return
     */
    public Boolean putDocs(String dbName, JsonObject docs) {
        Database db = client.database(dbName, false);
        Response resp = db.post(docs);

        return resp.getStatusCode() == 0;
    }

    /**
     * Update document to cloudant db
     * @param docs as Json type document
     * @return
     */
    public Boolean updateDocs(String dbName, JsonObject docs) {
        Database db = client.database(dbName, false);
        Response resp = db.update(docs);

        return resp.getStatusCode() == 201;
    }

     /**
     * Delete document from cloudant db
     * @param docs as Json type document
     * @return
     */
    public Boolean removeDocs(String dbName, JsonObject docs) {
        Database db = client.database(dbName, false);
        Response resp = db.remove(docs);

        return resp.getStatusCode() == 200;
    }

    public List<Row<String, JsonObject>> getDocsUsingViewByKey(String dbName, String documentName, String viewName, String key) {
        Database db = client.database(dbName, false);
        List<Row<String, JsonObject>> docList = null;
        try {
            docList = db.getViewRequestBuilder(documentName, viewName)
             .newRequest(Key.Type.STRING, JsonObject.class)
              .startKey(key)
              .endKey(key)
              .build()
            .getResponse().getRows();
        } catch (NoDocumentException e) {
            System.out.printf("NoDocumentException: statusCode:%d, error:%s\n", e.getStatusCode(), e.getError());
        } catch (IOException e){
            System.out.printf("get io exception:%s\n", e.toString());
        }
        
        return docList;
    }

    public InputStream getAttachment (String dbName, String docId, String attachmentName) {
        Database db = client.database(dbName, false);

        return db.getAttachment(docId, attachmentName);
    }
    
    public byte[] getImage(String dbName, String docId){
        InputStream  attachmentStream = null;
        byte[] imageRawData = "".getBytes();
        
        List<JsonObject> docs = getDocsById(dbName, docId);
        if (docs.size() == 0) {

            return imageRawData;
        }
    
        JsonObject doc = docs.get(0);

        try {
            JsonObject attachments = doc.get("_attachments").getAsJsonObject();

            for (String attachment: attachments.keySet()) {
                attachmentStream = getAttachment(dbName, docId, attachment);
                break;
            }
            if (attachmentStream != null) {
                 imageRawData =  IOUtils.toByteArray(attachmentStream);
            }
        } catch (IOException e){
            System.out.printf("CloudantDB.getImage: IOException:%s\n", e.getMessage());
        } catch (NullPointerException e) {
            System.out.printf("CloudantDB.getImage: NullPointerException:%s\n", e.getMessage());
        }
        
        return imageRawData;
    }
}