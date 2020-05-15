package com.gemteks.merc.service.am.model;

public class ActionInfo{
    private String grp;
    private String ts;
    private String userID;
    private String cpID;
    private String roleID;
    private String dataSet;

    public ActionInfo (String grp, String ts, String userID, 
                    String cpID, String roleID, String dataSet) {
        this.grp = grp;
        this.ts = ts;
        this.userID = userID;
        this.cpID = cpID;
        this.roleID = roleID;
        this.dataSet = dataSet;
    }

    public void setGrp(String grp){
        this.grp = grp;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCpID(String cpID){
        this.cpID = cpID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
    
    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }

    public String getGrp() {
        return this.grp;
    }

    public String getTs() {
        return this.ts;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getCpID() {
        return this.cpID;
    }

    public String getRoleID() {
        return this.roleID;
    }

    public String getDataSet() {
        return this.dataSet;
    }

}