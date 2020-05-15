package com.gemteks.merc.service.am.model.mysql.projection;

import java.util.Date;

public interface LoginListItem {
    Integer getUserId();
    Integer getCpId();
    Integer getRoleId();
    String getUserName();
    String getUserPwd();
    String getNickName();
    String getGender();
    String getDeviceToken();
    Integer getDeviceType();
    String getPic();
    String getEmail();
    Integer getUserBlock();
    Integer getUserType();
    Date getCreateTime();
    Integer getCreateUser();
    Date getUpdateTime();
    Integer getUpdateUser();

    String getRoleName();
    Integer getDataset();
}
