package com.gemteks.merc.service.layout.common;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class HttpResponseMessage {
    private JsonObject responsMsg;
    
    public HttpResponseMessage(){
        this.responsMsg = new JsonObject();
    }

    public HttpResponseMessage(String responseCode, String responseMsg){
        this.responsMsg = new JsonObject();
        this.responsMsg.addProperty("responseCode", responseCode);
        this.responsMsg.addProperty("responseMsg", responseMsg);
    }

    public HttpResponseMessage(JsonObject responseObject) {
        this.responsMsg = responseObject;
    }

    public void setResponseObject(JsonObject responseObject) {
        this.responsMsg = responseObject;
    }
    public void addResponseCode(String responseCode){
        this.responsMsg.addProperty("responseCode", responseCode);
    }

    public void addResponseMsg(String responseMsg){
        this.responsMsg.addProperty("responseMsg", responseMsg);
    }

    public void appendField(String key, JsonElement element){
        responsMsg.add(key, element);
    }

    public void appendFieldAsString(String key, String element){
        responsMsg.addProperty(key, element);
    }

    public String toString() {
        return responsMsg.toString();
    }

    public HashMap<String, Object> toMap() {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
        return gson.fromJson(responsMsg, type);
    }
}
