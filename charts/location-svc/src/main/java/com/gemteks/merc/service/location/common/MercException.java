package com.gemteks.merc.service.location.common;

public class  MercException extends Exception {

    private static final long serialVersionUID = 3918519002688396882L;
    private String msgCode;

    public MercException(String code, String message){
        super(message);
        this.msgCode = code;
    }

    public String getCode(){
        return this.msgCode;
    }
}