package com.gemteks.merc.service.am.model.mysql;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "api_grp")
public class ApiGrp {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer grpId;

    private String grpName;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    public String getGrpName(){
        return grpName;
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
        return updateUser;
    }

    public void setGrpName(String grpName){
        this.grpName = grpName;
    }

    public void setGrpId(Integer grpId){
        this.grpId = grpId;
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