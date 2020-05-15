package com.gemteks.merc.service.layout.controller;

import java.util.Map;

import com.gemteks.merc.service.layout.common.HttpResponseMessage;
import com.gemteks.merc.service.layout.common.MercException;
import com.gemteks.merc.service.layout.service.AvatarService;

import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AvatarApi {

    @Autowired
    private AvatarService avatarService;

    @GetMapping(value = "/avatar/{id}")
    public ResponseEntity<byte[]> getLogoImage(@PathVariable("id") String id, @RequestParam(name="token", required = false) String token){

        HttpHeaders respHeaders = new HttpHeaders();
        try {
            
            respHeaders.set("Content-Type", "image/png");
            return ResponseEntity.ok()
                .headers(respHeaders)
                .body(avatarService.getAvatarImage(token, id));

        } catch (MercException e) {
            HttpResponseMessage respMsg = new HttpResponseMessage();
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
           
            respHeaders.set("Content-Type", "application/json");
            return ResponseEntity.ok()
                .headers(respHeaders)
                .body(respMsg.toString().getBytes());
        }
    }

    @GetMapping(value = "/avatar", produces = "application/json")
    public  String getAvatarList(@RequestParam(required = false) String token){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            JsonArray avatarList = avatarService.getAvatarInfo(token);

            respMsg.appendField("avatar", avatarList);
            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("query success");

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toString();
    }

    @PostMapping(value = "/avatar", consumes = "multipart/form-data")
    public Map<String, Object> insertOrUpdateAvatarInfo(@RequestParam(value = "myFile", required = false) MultipartFile file, HttpServletRequest request) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            Integer putDocStatus = avatarService.insertOrUpdateAvatarInfo(file, request);
            respMsg.addResponseCode("000");
            respMsg.addResponseMsg(putDocStatus == 1? "Insert success":"Update success");

        }catch (MercException e) {

            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());

        }

        return respMsg.toMap();
    }

    @DeleteMapping(value = "/avatar")
    public Map<String, Object> delAvatarDoc(@RequestBody Map<String, String> paramater) {

        HttpResponseMessage respMsg = new HttpResponseMessage();
        try {

            if (avatarService.delAvatar(paramater)) {
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

    @PutMapping(value = "/avatar")
    public Map<String, Object> putUserAvatar(@RequestBody Map<String, String> paramater) {
        
        HttpResponseMessage respMsg = new HttpResponseMessage();
        try {

            if (avatarService.updateUserAvatar(paramater)) {
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("update success");
            }else {
                respMsg.addResponseCode("404");
                respMsg.addResponseMsg("No Data");
            }
            
        }catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }

    @PostMapping(value = "/avatar/confirm")
    public Map<String, Object> confirmAvatar(@RequestBody Map<String, String> paramater) {
        HttpResponseMessage respMsg = new HttpResponseMessage();
        try {
            String updatePicId = avatarService.confirmAvatar(paramater);
            if (!updatePicId.equals("")){
                respMsg.addResponseCode("000");
                respMsg.appendFieldAsString("pic", updatePicId);
                respMsg.addResponseMsg("Update success");
            } else {
                respMsg.addResponseCode("404");
                respMsg.addResponseMsg("No data");
            }

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }
        return respMsg.toMap();
    }

    @PostMapping(value = "/avatar/reset")
    public Map<String, Object> resetAvatar(@RequestBody Map<String, String> paramater) {
        HttpResponseMessage respMsg = new HttpResponseMessage();
        try {

            if (avatarService.resetAvatar(paramater)){
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("Update success");
            }

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }
        return respMsg.toMap();
    }

}