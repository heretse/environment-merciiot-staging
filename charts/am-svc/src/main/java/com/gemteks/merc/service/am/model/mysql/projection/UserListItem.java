package com.gemteks.merc.service.am.model.mysql.projection;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

public interface UserListItem {
    Integer getUserId();
    Integer getCpId();
    Integer getRoleId();
    String getUserName();
    String getPic();
    String getEmail();
    Integer getUserBlock();
    Integer getUserType();
    Date getCreateTime();
    String getCpName();
    String getRoleName();
    String getBlockDesc();
}
