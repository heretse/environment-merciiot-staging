package com.gemteks.merc.service.am.model.mongodb;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Devices {

    @Id
    private String id;

    private String macAddr;
    private String data;
    private Date recv;
    private Map<String, Object> extra = new HashMap<>();
    private Map<String, Object> information = new HashMap<>();
    private Double timestamp;
    private String date;

    public String getId(){
        return id;
    }

    public String getMacAddr(){
        return macAddr;
    }

    public String getData(){
        return data;
    }

    public Date getRecv(){
        return recv;
    }

    public Map<String, Object> getExtra(){
        return extra;
    }

    public Map<String, Object> getInformation(){
        return information;
    }

    public Double getTimeStamp(){
        return timestamp;
    }

    public String getDate(){
        return date;
    }


    public void setId(String id){
        this.id = id;
    }

    public void setMacAddr(String macAddr){
        this.macAddr = macAddr;
    }

    public void setData(String data){
        this.data = data;
    }

    public void setRecv(Date recv){
        this.recv = recv;
    }

    public void setExtra(Map<String, Object> extra){
        this.extra = extra;
    }

    public void setInformation(Map<String, Object> information){
        this.information = information;
    }

    public void setTimeStamp(Double timestamp){
        this.timestamp = timestamp;
    }

    public void setDate(String date){
        this.date = date;
    }


}