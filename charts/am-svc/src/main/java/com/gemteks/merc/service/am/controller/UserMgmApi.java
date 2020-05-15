package com.gemteks.merc.service.am.controller;

import java.util.Map;
import com.gemteks.merc.service.am.common.HttpResponseMessage;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.service.UserMgmService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserMgmApi {

    @Autowired
    private UserMgmService userMgmSvr;

    @GetMapping(value = "/users", produces = "application/json")
    public String getUserList(@RequestParam(required = false) String token, @RequestParam(required = false) String search){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            JsonArray userList = userMgmSvr.getUserList(token, search);

            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("query success");
            respMsg.appendFieldAsString("size", String.valueOf(userList.size()));
            respMsg.appendField("users", userList);

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toString();
    }

    @PutMapping(value = "/users", produces = "application/json")
    public String updateUserStatus(@RequestBody Map<String, String> parameter){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            if (userMgmSvr.updateUserStatus(parameter)){
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("update success");
            }
            else {
                respMsg.addResponseCode("404");
                respMsg.addResponseMsg("No Data");
            }

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toString();
    }

    @DeleteMapping(value = "/users")
    public Map<String, Object> delUser(@RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();
        try {

            if (userMgmSvr.delUser(parameter)) {
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("delete success");
            }else {
                respMsg.addResponseCode("999");
                respMsg.addResponseMsg("delete fail");
            }

        }catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }

    @PostMapping(value = "/users")
    public Map<String, Object> insertUserByAdmin(@RequestBody Map<String, String> userInfo) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            if (userMgmSvr.insertUserByAdmin(userInfo)){
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("insert success");
            }
            else {
                respMsg.addResponseCode("999");
                respMsg.addResponseMsg("insert fail");
            }

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }
}
