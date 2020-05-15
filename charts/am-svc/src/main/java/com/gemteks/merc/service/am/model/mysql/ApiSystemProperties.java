package com.gemteks.merc.service.am.model.mysql;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "api_system_properties")
public class ApiSystemProperties {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "p_name", nullable = false)
    private String pName;

    @Column(name = "p_value", nullable = false)
    private String pValue;

    @Column(name = "p_desc")
    private String pDesc;

    @Column(name = "p_type", nullable = false)
    private String pType;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    public String getPName(){
        return pName;
    }

    public String getPValue(){
        return pValue;
    }

    public String getPDesc(){
        return pDesc;
    }

    public String getPType(){
        return pType;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public Integer getCreateUser(){
        return createUser;
    }

    public Date getUpdateTime(){
        return updateTime;
    }

    public Integer getUpdateUser(){
        return updateUser;
    }


    public void getPName(String pName){
        this.pName = pName;
    }

    public void getPValue(String pValue){
        this.pValue = pValue;
    }

    public void getPDesc(String pDesc){
        this.pDesc = pDesc;
    }

    public void getPType(String pType){
        this.pType = pType;
    }


    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public void setCreateUser(Integer createUser){
        this.createUser = createUser;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    public void setUpdateUser(Integer updateUser){
        this.updateUser = updateUser;
    }
}