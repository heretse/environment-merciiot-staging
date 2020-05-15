package com.gemteks.merc.service.location.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
}