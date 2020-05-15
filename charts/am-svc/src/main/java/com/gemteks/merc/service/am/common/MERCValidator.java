package com.gemteks.merc.service.am.common;

import com.google.gson.JsonArray;

public class MERCValidator {
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

    public static void checkAccessAuthority(String grpIds) throws MercException{
        boolean accessFlg = false;
        for (String grpId : grpIds.split(",")){
            if(grpId.equals("29")){
                accessFlg = true;
            }
        }
        if (!accessFlg){
            throw new MercException("401", "no permission to access");
        }
    }
}