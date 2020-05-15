package com.gemteks.merc.service.am.model.mysql.projection;

import java.util.Date;

public interface SysPropertyListItem {
    String getP_name();
    String getP_value();
    String getP_desc();
    String getP_type();
    Date getCreateTime();
}
