package com.gemteks.merc.service.location.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DeserializeJson {
    public static JsonObject toObject(String jsonStr) {
        Gson gson = new Gson();    
        return gson.fromJson(jsonStr, JsonObject.class);
    }
}