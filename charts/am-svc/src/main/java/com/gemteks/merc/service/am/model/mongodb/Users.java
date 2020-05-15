package com.gemteks.merc.service.am.model.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="users")
public class Users {

    @Id
    private String id;
    private String userId;

    public Users() {}

    public Users(String id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public String getId(){
        return id;
    }

    public String getUserId(){
        return userId;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setUserId(String macAddr){
        this.userId = userId;
    }

}
