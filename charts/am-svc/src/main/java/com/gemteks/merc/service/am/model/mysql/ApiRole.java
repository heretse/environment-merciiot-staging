package com.gemteks.merc.service.am.model.mysql;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "api_role")
public class ApiRole {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer roleId;

    private String roleName;

    @Column(nullable = false)
    private Integer dataset;

    @Column(nullable = false)
    private Integer editFlg;

    @Column(nullable = false)
    private Integer delFlg;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    public Integer getRoleId(){
        return roleId;
    }

    public String getRoleName(){
        return roleName;
    }

    public Integer getDataset(){
        return dataset;
    }

    public Integer getEditFlg(){
        return editFlg;
    }

    public Integer getDelFlg(){
        return delFlg;
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

    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    public void setDataset(Integer dataset){
        this.dataset = dataset;
    }

    public void getEditFlg(Integer editFlg){
        this.editFlg= editFlg;
    }

    public void getDelFlg(Integer delFlg){
        this.delFlg = delFlg;
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