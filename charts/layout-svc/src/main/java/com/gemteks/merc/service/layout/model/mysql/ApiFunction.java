package com.gemteks.merc.service.layout.model.mysql;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "api_function")
public class ApiFunction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer functionId;

    private String functionName;

    private String functionUrl;

    private Integer parentId;

    private Integer sortId;

    private String hiddenFlg;

    @Column(nullable = false)
    private Integer delFlg;

    @Column(nullable = false)
    private Integer grpId;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    public Integer getFunctionId(){
        return functionId;
    }

    public String getFunctionName(){
        return functionName;
    }

    public String getFunctinUrl(){
        return functionUrl;
    }

    public Integer getParentId(){
        return parentId;
    }

    public Integer getSortId(){
        return sortId;
    }

    public String getHiddenFlg() {
        return hiddenFlg;
    }

    public Integer getDelFlag(){
        return delFlg;
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

    public void setFunctionId(Integer functionId){
        this.functionId = functionId;
    }

    public void setFunctionName(String functionName){
        this.functionName = functionName;
    }

    public void setFunctinUrl(String functionUrl){
        this.functionUrl = functionUrl;
    }

    public void setParentId(Integer parentId){
        this.parentId = parentId;
    }

    public void setSortId(Integer sortId){
        this.sortId = sortId;
    }

    public void setHiddenFlg(String hiddenFlg) {
        this.hiddenFlg = hiddenFlg;
    }

    public void setDelFlag(Integer delFlg){
        this.delFlg = delFlg;
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