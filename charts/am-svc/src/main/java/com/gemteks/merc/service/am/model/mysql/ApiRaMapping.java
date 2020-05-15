package com.gemteks.merc.service.am.model.mysql;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "api_ra_mapping")
public class ApiRaMapping {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer raId;

    @Column(nullable = false)
    private Integer roleId;

    @Column(nullable = false)
    private Integer grpId;

    private Integer sortId;

    @Column(nullable = false)
    private Integer createFlg;

    @Column(nullable = false)
    private Integer updateFlg;

    @Column(nullable = false)
    private Integer deleteFlg;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    public Integer getRaId(){
        return raId;
    }

    public Integer getRoleId(){
        return roleId;
    }

    public Integer getGrpId(){
        return grpId;
    }

    public Integer getSortId(){
        return sortId;
    }

    public Integer getCreateFlg(){
        return createFlg;
    }

    public Integer getUpdateFlg(){
        return updateFlg;
    }

    public Integer getDeleteFlg(){
        return deleteFlg;
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

    public void setRaId(Integer raId){
        this.raId = raId;
    }

    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }

    public void setGrpId(Integer grpId){
        this.grpId = grpId;
    }

    public void setSortId(Integer sortId){
        this.sortId = sortId;
    }

    public void setCreateFlg(Integer createFlg){
        this.createFlg = createFlg;
    }

    public void setUpdateFlg(Integer updateFlg){
        this.updateFlg = updateFlg;
    }

    public void setDeleteFlg(Integer deleteFlg){
        this.deleteFlg = deleteFlg;
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