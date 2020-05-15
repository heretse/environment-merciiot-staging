package com.gemteks.merc.service.am.model.mysql;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "api_user_mapping")
public class ApiUserMapping {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer mappingId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer locId;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    /**
     * Getter
     */
    public Integer getMappingId(){
        return mappingId;
    }

    public Integer getUserId(){
        return userId;
    }

    public Integer getLocId(){
        return locId;
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

    /**
     * Setter
     */
    public void setMappingId(Integer mappingId){
        this.mappingId = mappingId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public void setLocId(Integer locId){
        this.locId = locId;
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