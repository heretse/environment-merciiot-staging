package com.gemteks.merc.service.location.common;

import com.google.gson.JsonArray;

public class MercValidator {
    public static void validateParam(String param) throws MercException {
        if (param == null || param.length() == 0 || param.equals("")) {
            throw new MercException("999", "Missing parameter");
        }
    }
    public static void validateLayout(JsonArray layoutList) throws MercException {
        if (layoutList.size() == 0) {
            throw new MercException("999", "Missing parameter");
        }
    }

    public static void validateDataLenForToken(String[] dataList) throws MercException {
        if (dataList.length != 6) {
            throw new MercException("999", "Token length error");
        }
    }

}