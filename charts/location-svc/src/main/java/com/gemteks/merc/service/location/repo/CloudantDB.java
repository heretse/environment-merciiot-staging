package com.gemteks.merc.service.location.repo;

import com.google.gson.JsonObject;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.query.QueryResult;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.ViewResponse.Row;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.cloudant.client.api.model.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Configuration
@PropertySource("classpath:application.properties")
public class CloudantDB {
    @Value("${cloudant.db}")
    private String cloudantDBName;

    @Autowired
    private CloudantClient client;

    public List<JsonObject> getDocsById(String id) {
        Database db = client.database(this.cloudantDBName, false);
        String query = String.format("{\"selector\":{\"_id\": \"%s\"}}", id);
        QueryResult<JsonObject> qrList = db.query(query, JsonObject.class);

        return qrList.getDocs();
    }

    public Boolean putDocs(JsonObject docs) {
        Database db = client.database(this.cloudantDBName, false);
        Response resp = db.post(docs);

        return resp.getStatusCode() == 0;
    }

    public Boolean updateDocs(JsonObject docs) {
        Database db = client.database(this.cloudantDBName, false);
        Response resp = db.update(docs);

        return resp.getStatusCode() == 201;
    }

    public Boolean removeDocs(JsonObject docs) {
        Database db = client.database(this.cloudantDBName, false);
        Response resp = db.remove(docs);

        return resp.getStatusCode() == 200;
    }

    public List<Row<String, JsonObject>> getDocsUsingViewByKey(String documentName, String viewName, String key) {
        Database db = client.database(this.cloudantDBName, false);
        List<Row<String, JsonObject>> docList = null;
        try {
            docList = db.getViewRequestBuilder(documentName, viewName)
             .newRequest(Key.Type.STRING, JsonObject.class)
              .keys(key)
              .build()
            .getResponse().getRows();
        } catch (NoDocumentException e) {
            System.out.printf("NoDocumentException: statusCode:%d, error:%s\n", e.getStatusCode(), e.getError());
        } catch (IOException e){
            System.out.printf("get io exception:%s\n", e.toString());
        }
        
        return docList;
    }

    public InputStream getAttachment (String docId, String attachmentName) {
        Database db = client.database(this.cloudantDBName, false);

        return db.getAttachment(docId, attachmentName);
    }
}