package com.gemteks.merc.service.am.model.mysql;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "api_user_social_mapping")
public class ApiUserSocialMapping {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer mappingId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private String socialType;

    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    private Date createTime;

    @Column(nullable = false)
    private Integer createUser;

    /**
     * Getter
     */

    public Integer getMappingId(){
        return mappingId;
    }

    public Integer getUserId(){
        return userId;
    }

    public String getSocialType(){
        return socialType;
    }

    public String getSocialId(){
        return socialId;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public Integer getCreateUser(){
        return createUser;
    }
    /**
     * Setter
     */

    public void setMappingId(Integer mappingId){
        this.mappingId = mappingId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public void setSocialType(String socialType){
        this.socialType = socialType;
    }

    public void setSocialId(String socialId){
        this.socialId = socialId;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public void setCreateUser(Integer createUser){
        this.createUser = createUser;
    }
}