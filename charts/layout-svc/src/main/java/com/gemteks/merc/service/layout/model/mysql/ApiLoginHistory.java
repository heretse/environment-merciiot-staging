package com.gemteks.merc.service.layout.model.mysql;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "api_login_history")
public class ApiLoginHistory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer historyId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String userToken;

    @Column(name = "history_login_time", nullable = false)
    private Date historyLoginTime;

    @Column(name = "history_logout_time")
    private Date historyLogoutTime;

    @Column(name = "history_ip")
    private String historyIP;

    @Column(name = "history_type", nullable = false)
    private Integer historyType;

    @Column(nullable = false)
    private Date createTime;


    public Integer getHistoryId(){
        return historyId;
    }

    public Integer getUserId(){
        return userId;
    }

    public String getUserToken(){
        return userToken;
    }

    public Date getHistoryLoginTime(){
        return historyLoginTime;
    }

    public Date getHistoryLogoutTime(){
        return historyLogoutTime;
    }

    public String getHistoryIp(){
        return historyIP;
    }

    public Integer getHistoryType(){
        return historyType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setHistoryId(Integer historyId){
        this.historyId = historyId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public void setUserToken(String userToken){
        this.userToken = userToken; 
    }

    public void setHistoryLoginTime(Date historyLoginTime){
        this.historyLoginTime = historyLoginTime;
    }

    public void setHistoryLogoutTime(Date historyLogoutTime){
        this.historyLogoutTime = historyLogoutTime;
    }

    public void setHistoryIp(String historyIp){
        this.historyIP = historyIp;
    }

    public void setHistoryType(Integer historyType){
        this.historyType = historyType;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

