package com.gemteks.merc.service.am;

import java.util.List;
import com.gemteks.merc.service.am.repo.cloudant.CloudantDB;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
class CloudantTests {
    @Autowired
    private CloudantDB cloudantDB_Client;

    @Test 
    public void testCloudant() {
        List<JsonObject> docs = cloudantDB_Client.getDocsById("1_layout");
        if (docs.size() == 0) {
            System.out.println("To Get the number of documents from CloudantDB is zero.");
            return;
        }

        System.out.println(docs.get(0));
    }

}