package com.gemteks.merc.service.am.model.mysql.projection;

import java.util.Date;

public interface RoleListItem {
    Integer getRoleId();
    String getRoleName();
    Integer getDataset();
    Integer getEditFlg();
    Integer getDelFlg();
    Date getCreateTime();
}
