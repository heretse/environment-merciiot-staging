package com.gemteks.merc.service.am.model.mysql.projection;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

public interface RaMappingListItem {
    Integer getGrpId();
    String getGrpName();
    Date getCreateTime();
    Integer getCreateFlg();
    Integer getUpdateFlg();
    Integer getDeleteFlg();
    Integer getSortId();
}
