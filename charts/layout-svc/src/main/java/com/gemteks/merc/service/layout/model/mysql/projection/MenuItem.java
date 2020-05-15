package com.gemteks.merc.service.layout.model.mysql.projection;

/**
 * Use interface-based projection
 */
public interface MenuItem{
    Integer getFunctionId();
    String getFunctionName();
    String getFunctionUrl();
    String getHiddenFlg();
    Integer getParentId();
}