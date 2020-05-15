package com.gemteks.merc.service.am.model.mysql;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
//For cloud_dev database
@Entity
@Table(name="api_cp")
public class ApiCp {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer cpId;

    @Column(nullable = false)
    private String cpName;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    @Column(nullable = false)
    private Date updateTime;

    @Column(nullable = false)
    private Integer updateUser;

    public ApiCp() {
    }

    public ApiCp(Integer cpId, String cpName, Date createTime, Integer createUser, Date updateTime, Integer updateUser) {
        this.cpId = cpId;
        this.cpName = cpName;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    public Integer getCpId(){
        return cpId;
    }

    public String getCpName(){
        return cpName;
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
        return  updateUser;
    }

    public void setCpId(Integer cpId){
        this.cpId = cpId;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public void setCreateTime(Date time) {
        this.createTime = time;
    }

    public void setCreateUser(Integer user){
        this.createUser = user;
    }

    public void setUpdateTime(Date time){
        this.updateTime = time;
    }

    public void setUpdateTime(Integer user){
        this.updateUser = user;
    }
}