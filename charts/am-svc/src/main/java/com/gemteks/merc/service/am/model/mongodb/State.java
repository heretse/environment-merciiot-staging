package com.gemteks.merc.service.am.model.mongodb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class State {

	@Id
	private String id;
    private Map<String, Object> extra = new HashMap<>();
    private String macAddr;
    private Map<String, String> information = new HashMap<>();
    private Double createTs;
    private String createTime;
    private Double last_updated;
    private Double last_changed;
    private Map<String, String> pre_information = new HashMap<>();


    public String getId(){
        return id;
    }

    public Map<String, Object> getExtra(){
        return extra;
    }

    public String getMacAddr(){
        return macAddr;
    }

    public Map<String, String> getInformation(){
        return information;
    }

    public Double getCreateTs(){
        return createTs;
    }

    public String getCreateTime(){
        return createTime;
    }

    public Double getLastUpdated(){
        return last_updated;
    }

    public Double getLastChanged(){
        return last_changed;
    }

    public Map<String, String> getPreInformation(){
        return pre_information;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setExtra(Map<String, Object> extra){
        this.extra = extra;
    }

    public void setMacAddr(String macAddr){
        this.macAddr = macAddr;
    }

    public void setInformation(Map<String, String> information){
        this.information = information;
    }

    public void setCreateTs(Double createTS){
        this.createTs = createTS;
    }

    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }

    public void setLastUpdated(Double last_updated){
        this.last_updated = last_updated;
    }

    public void setLastChanged(Double last_changed){
        this.last_changed = last_changed;
    }

    public void setPreInformation(Map<String, String> pre_information){
        this.pre_information = pre_information;
    }

}