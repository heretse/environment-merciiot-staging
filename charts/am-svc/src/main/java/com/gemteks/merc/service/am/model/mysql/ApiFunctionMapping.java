package com.gemteks.merc.service.am.model.mysql;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "api_function_mapping")
public class ApiFunctionMapping {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer roleId;

    @Column(nullable = false)
    private Integer functionId;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    public Integer getRoleId(){
        return roleId;
    }

    public Integer getFunctionId(){
        return functionId;
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

    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }

    public void setFunctionId(Integer functionId){
        this.functionId = functionId;
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