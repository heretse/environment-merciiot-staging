package com.gemteks.merc.service.layout.model.mysql;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "api_user")
public class ApiUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer cpId;

    @Column(nullable = false)
    private Integer roleId;

    private String userName;

    private String userPwd;

    private String nickName;

    private String gender;

    private String devicveToken;

    @Column(nullable = false)
    private Integer deviceType;

    private String pic;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer userBlock;

    @Column(nullable = false)
    private Integer userType;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    /*getter*/
    public Integer getUserId(){
        return userId;
    }

    public Integer getCpId(){
        return cpId;
    }

    public Integer getRoleId(){
        return roleId;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserPwd(){
        return userPwd;
    }

    public String getNickName() {
        return nickName;
    }

    public String getGender(){
        return gender;
    }

    public String getDeviceToken(){
        return devicveToken;
    }

    public Integer getDeviceType(){
        return deviceType;
    }

    public String getPic(){
        return pic;
    }

    public String getEmail(){
        return email;
    }

    public Integer getUserBlock(){
        return userBlock;
    }

    public Integer getUserType(){
        return userType;
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

    /*setter*/
    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public void setCpId(Integer cpId){
        this.cpId = cpId;
    }

    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void getUserPwd(String userPwd){
        this.userPwd = userPwd; 
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public void setDeviceToken(String deviceToken){
        this.devicveToken = deviceToken;
    }

    public void setDeviceType(Integer deviceType){
        this.deviceType = deviceType;
    }

    public void setPic(String pic){
        this.pic = pic;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setUserBlock(Integer userBlock){
        this.userBlock = userBlock; 
    }

    public void setUserType(Integer userType){
        this.userType = userType;
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