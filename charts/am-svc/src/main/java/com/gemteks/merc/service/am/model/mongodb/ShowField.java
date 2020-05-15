package com.gemteks.merc.service.am.model.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="show_field")
public class ShowField {
    @Id
    private String id;

    private String deviceType;
    private String typeName;
    private String icon;
    private String showTitle;
    private Map<String, Object> tab = new HashMap<>();
    private Integer keepAlive;
    private Map<String, Object> showField = new HashMap<>();
    private List<Map<String,Object>> searchField = new ArrayList();
    private List<Map<String,Object>> metaField = new ArrayList();
    private List<Map<String,Object>> securityField = new ArrayList();
    private String createUser;
    private Date createTime;

    public String getId(){
        return id;
    }

    public String getDeviceType(){
        return deviceType;
    }

    public String getTypeName(){
        return typeName;
    }

    public String getIcon(){
        return icon;
    }

    public String getShowTitle(){
        return showTitle;
    }

    public Map<String, Object> getTab(){
        return tab;
    }

    public Integer getKeepAlive(){
        return keepAlive;
    }

    public Map<String, Object> getShowField(){
        return showField;
    }

    public List<Map<String,Object>> getSearchField(){
        return searchField;
    }

    public List<Map<String,Object>> getMetaField(){
        return metaField;
    }

    public List<Map<String,Object>> getSecurityField(){
        return securityField;
    }
    public String getCreateUser(){
        return createUser;
    }

    public Date getCreateTime(){
        return createTime;
    }




    public void setId(String id){
        this.id = id;
    }

    public void setDeviceType(String deviceType){
        this.deviceType = deviceType;
    }

    public void setTypeName(String typeName){
        this.typeName = typeName;
    }

    public void setIcon(String icon){
        this.icon = icon;
    }

    public void setShowTitle(String showTitle){
        this.showTitle = showTitle;
    }

    public void setTab(Map<String, Object> tab){
        this.tab = tab;
    }

    public void setKeepAlive(Integer keepAlive){
        this.keepAlive = keepAlive;
    }

    public void setShowField(Map<String, Object> showField){
        this.showField = showField;
    }

    public void setSearchField(List<Map<String,Object>> searchField){
        this.searchField = searchField;
    }

    public void setMetaField(List<Map<String,Object>> metaField){
        this.metaField = metaField;
    }

    public void setSecurityField(List<Map<String,Object>> securityField){
        this.securityField = securityField;
    }

    public void setCreateUser(String createUser){
        this.createUser = createUser;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

}