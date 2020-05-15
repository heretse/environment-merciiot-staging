package com.gemteks.merc.service.am.model.mysql;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "api_cp_mapping")
public class ApiCpMapping {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer acId;

    @Column(nullable = false)
    private Integer cpId;

    @Column(nullable = false)
    private Integer grpId;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    @Column(nullable = false)
    private Date updateTime;

    @Column(nullable = false)
    private Integer updateUser;

    public Integer getAcId(){
        return acId;
    }

    public Integer getCpId(){
        return cpId;
    }

    public Integer getGrpId(){
        return grpId;
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

    public void setAcId(Integer acId){
        this.acId = acId;
    }

    public void setCpId(Integer cpId){
        this.cpId = cpId;
    }

    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
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