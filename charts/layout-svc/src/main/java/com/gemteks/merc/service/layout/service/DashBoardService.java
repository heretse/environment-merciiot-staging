package com.gemteks.merc.service.layout.service;

import java.util.List;

import com.gemteks.merc.service.layout.common.DeserializeJson;
import com.gemteks.merc.service.layout.common.MercException;
import com.gemteks.merc.service.layout.model.ActionInfo;
import com.gemteks.merc.service.layout.repo.cloudant.CloudantDB;
import com.gemteks.merc.service.layout.repo.mysql.MenuItemRepository;
import com.gemteks.merc.service.layout.model.mysql.projection.MenuItem;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(value="prototype")
@PropertySource("classpath:application.properties")
public class DashBoardService {

    private String dbName = "web_info";

    @Autowired
    private CloudantDB cloudantDBInstance;

    @Autowired
    private MenuItemRepository menuItemDataRepository;

    public JsonArray getDashBoardLayoutInfo(ActionInfo actionInfo) throws MercException {
        String searchID = String.format("%s_%s_layout", actionInfo.getCpID(), actionInfo.getUserID());

        System.out.printf("search id:%s", searchID);
        
        List<JsonObject> docsForDB = cloudantDBInstance.getDocsById(dbName, searchID);

        if (docsForDB.size() == 0) {
            searchID = String.format("%s_layout", actionInfo.getCpID());
            docsForDB = cloudantDBInstance.getDocsById(dbName, searchID);

            if (docsForDB.size() == 0) {
                throw new MercException("404", "No data");
            }
        }
        return docsForDB.get(0).getAsJsonArray("layout");
    }

    public Integer putDashBoardLayoutInfo(ActionInfo actionInfo, String token, JsonArray layoutList)
            throws MercException {
        String searchID;
        Integer putDocStatus = 1;
    
        if (actionInfo.getDataSet().equals("0")) {
            searchID = String.format("%s_layout", actionInfo.getCpID());
        } else {
            searchID = String.format("%s_%s_layout", actionInfo.getCpID(), actionInfo.getUserID());
        }
 
        List<JsonObject> docsForDB = cloudantDBInstance.getDocsById(dbName, searchID);

        JsonObject jsonDoc = DeserializeJson.toObject("{}");

        jsonDoc.addProperty("_id", searchID);
        jsonDoc.add("layout", layoutList);
        jsonDoc.addProperty("token", token);

        if (docsForDB.size() == 0) {
                    
            System.out.printf("jsonDoc:%s for create\n", jsonDoc.toString());
            if (cloudantDBInstance.putDocs(dbName, jsonDoc) == false) {
                throw new MercException("999", "Insert layout fail");
            }

        } else {
            JsonElement _rev = docsForDB.get(0).get("_rev");
            jsonDoc.add("_rev", _rev);

            if (cloudantDBInstance.updateDocs(dbName, jsonDoc) == false ) {
                throw new MercException("999", "Update layout fail");
            }
            putDocStatus = 2;
            System.out.printf("jsonDoc:%s for update\n", jsonDoc.toString());
        }

        return putDocStatus;
    }

    public JsonArray getMenuItemList(Integer grpId, Integer roleId) throws MercException {
        List<MenuItem>  menuItemList = menuItemDataRepository.findAllMenuItemBy(roleId, grpId);

        if (menuItemList.size() == 0) {
            throw new MercException("404", "no menu item");
        }
        JsonArray jsonArrayForMenu = new JsonArray();
        for (MenuItem menuItem: menuItemList) {
            List<MenuItem>  subMenuItemList = menuItemDataRepository.findAllMenuItemByParentId(menuItem.getFunctionId());

            JsonObject jsonItem = new JsonObject();
            jsonItem.addProperty("functionId", menuItem.getFunctionId());
            jsonItem.addProperty("functionName", menuItem.getFunctionName());
            jsonItem.addProperty("functionUrl", menuItem.getFunctionUrl());
            jsonItem.addProperty("hiddenFlg", menuItem.getHiddenFlg());
            jsonItem.addProperty("parentId", menuItem.getParentId());
            jsonItem.addProperty("child", subMenuItemList.size() > 0?true:false);
            if (subMenuItemList.size() > 0){
                JsonArray jsonArrayForSubMenu = new JsonArray();
                for (MenuItem subMenuItem: subMenuItemList) {
                    JsonObject jsonSubItem = new JsonObject();
                    jsonSubItem.addProperty("functionId", subMenuItem.getFunctionId());
                    jsonSubItem.addProperty("functionName", subMenuItem.getFunctionName());
                    jsonSubItem.addProperty("functionUrl", subMenuItem.getFunctionUrl());
                    jsonSubItem.addProperty("hiddenFlg", subMenuItem.getHiddenFlg());
                    jsonSubItem.addProperty("parentId", subMenuItem.getParentId());
                    jsonArrayForSubMenu.add(jsonSubItem);
                }
                jsonItem.add("subfunc", jsonArrayForSubMenu);
            }

            jsonArrayForMenu.add(jsonItem);
        }

        return jsonArrayForMenu;
    }
}