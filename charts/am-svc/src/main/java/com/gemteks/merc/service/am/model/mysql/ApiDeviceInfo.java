package com.gemteks.merc.service.am.model.mysql;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "api_device_info")
public class ApiDeviceInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer deviceId;

    @Column(name = "device_mac", nullable = false)
    private String deviceMac;

    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @Column(name = "device_status", nullable = false)
    private Integer deviceStatus;

    @Column(name = "device_type", nullable = false)
    private String deviceType;

    @Column(name = "device_share", nullable = false)
    private Integer deviceShare;

    @Column(name = "device_active_time", nullable = false)
    private Date deviceActiveTime;

    @Column(name = "device_bind_time", nullable = false)
    private Date deviceBindTime;

    @Column(name = "device_cp_id", nullable = false)
    private Integer deviceCpId;

    @Column(name = "device_user_id", nullable = false)
    private Integer deviceUserId;

    @Column(name = "device_Iot_org", nullable = false)
    private String deviceIotOrg;

    @Column(name = "device_Iot_type", nullable = false)
    private String deviceIotType;

    @Column(name = "device_Iot_secret", nullable = false)
    private String deviceIotSecret;

    @Column(nullable = false)
    private String remark;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    private Date updateTime;

    private Integer updateUser;

    /** 
     * Getter
     */
    public Integer getDeviceId(){
        return this.deviceId;
    }

    public String getDeviceMac(){
        return this.deviceMac;
    }

    public String getDeviceName(){
        return this.deviceName;
    }

    public Integer getDeviceStatus(){
        return this.deviceStatus;
    }

    public String getDeviceType(){
        return this.deviceType;
    }

    public Integer getDeviceShare(){
        return this.deviceShare;
    }

    public Date getDeviceActiveTime(){
        return this.deviceActiveTime;
    }

    public Date getDeviceBindTime(){
        return this.deviceBindTime;
    }

    public Integer getDeviceCpId(){
        return deviceCpId;
    }

    public Integer getDeviceUserId(){
        return deviceUserId;
    }

    public String getDeviceIotOrg(){
        return deviceIotOrg;
    }

    public String getDeviceIotType(){
        return deviceIotType;
    }

    public String getDeviceIotSecret(){
        return deviceIotSecret;
    }

    public String getRemark(){
        return remark;
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

    /** 
     * Setter 
     */
    public void setDeviceId(Integer deviceId){
        this.deviceId = deviceId;
    }

    public void setDeviceMac(String deviceMac){
        this.deviceMac = deviceMac;
    }

    public void setDeviceName(String deviceName){
        this.deviceName = deviceName;
    }

    public void setDeviceStatus(Integer deviceStatus){
        this.deviceStatus = deviceStatus;
    }

    public void setDeviceType(String deviceType){
        this.deviceType = deviceType;
    }

    public void setDeviceShare(Integer deviceShare){
        this.deviceShare = deviceShare;
    }

    public void setDeviceActiveTime(Date deviceActiveTime){
        this.deviceActiveTime = deviceActiveTime;
    }

    public void getDeviceBindTime(Date deviceBindTime){
        this.deviceBindTime = deviceBindTime;
    }

    public void getDeviceCpId(Integer deviceCpId){
        this.deviceCpId = deviceCpId;
    }

    public void setDeviceUserId(Integer deviceUserId){
        this.deviceUserId = deviceUserId;
    }

    public void setDeviceIotOrg(String deviceIotOrg){
        this.deviceIotOrg = deviceIotOrg;
    }

    public void setDeviceIotType(String deviceIotType){
        this.deviceIotType = deviceIotType;
    }

    public void setDeviceIotSecret(String deviceIotSecret){
        this.deviceIotSecret = deviceIotSecret;
    }

    public void setRemark(String remark){
        this.remark = remark;
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