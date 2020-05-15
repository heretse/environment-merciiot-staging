package com.gemteks.merc.service.am.model.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Maps {
    @Id
    private String id;

    private String deviceType;
    private String spliteType;
    private Integer seperateFlg;
    private Integer trackFlg;
    private String typeName;
    private Map<String, Object> eventClean = new HashMap<>();
    private Map<String, Object> map = new HashMap<>();
    private List<Map<String,Object>> validator = new ArrayList();
    private List<Map<String,Object>> transform = new ArrayList();
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private String testV;

    public String getId(){
        return id;
    }

    public String getDeviceType(){
        return deviceType;
    }

    public String getSpliteType(){
        return spliteType;
    }

    public Integer getSeperateFlg(){
        return seperateFlg;
    }

    public Integer getTrackFlg(){
        return trackFlg;
    }

    public String getTypeName(){
        return typeName;
    }

    public Map<String, Object> getEvenClean(){
        return eventClean;
    }

    public Map<String, Object> getMap(){
        return map;
    }

    public List<Map<String,Object>> getValidator(){
        return validator;
    }

    public List<Map<String,Object>> getTransform(){
        return transform;
    }

    public String getCreateUser(){
        return createUser;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public String getUpdateUser(){
        return updateUser;
    }

    public Date getUpdateTime(){
        return updateTime;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setDeviceType(String deviceType){
        this.deviceType = deviceType;
    }

    public void setSpliteType(String spliteType){
        this.spliteType = spliteType;
    }

    public void setSeperateFlg(Integer seperateFlg){
        this.seperateFlg = seperateFlg;
    }

    public void setTrackFlg(Integer trackFlg){
        this.trackFlg = trackFlg;
    }

    public void setTypeName(String typeName){
        this.typeName = typeName;
    }

    public void setEvenClean(Map<String, Object> eventClean){
        this.eventClean = eventClean;
    }

    public void setMap(Map<String, Object> map){
        this.map = map;
    }

    public void setValidator(List<Map<String,Object>> validator){
        this.validator = validator;
    }

    public void setTransform(List<Map<String,Object>> transform){
        this.transform = transform;
    }

    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public void setUpdateUser(String updateUser){
        this.updateUser = updateUser;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}