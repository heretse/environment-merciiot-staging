package com.gemteks.merc.service.layout.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.gemteks.merc.service.layout.common.HttpResponseMessage;
import com.gemteks.merc.service.layout.common.MercException;
import com.gemteks.merc.service.layout.service.CorpInfoService;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CorpInfoApi {
    @Autowired
    private CorpInfoService corpInfoService;

    @PostMapping(value="/cpInfo", consumes = "multipart/form-data")
    public Map<String, Object> saveCorpInfo(@RequestParam(value = "myFile", required = false) MultipartFile  file, HttpServletRequest request){

        HttpResponseMessage respMsg = new HttpResponseMessage();
        try {

            Integer putDocStatus = corpInfoService.insertOrUpdateCorpInfo(file, request);
            respMsg.addResponseCode("000");
            respMsg.addResponseMsg(putDocStatus == 1 ? "Insert success" : "Update success"); 

        }catch (MercException e) {

            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());

        }

        return respMsg.toMap();
    }

    @GetMapping(value = "/cpInfo/logo/{id}")
    public ResponseEntity<byte[]> getLogoImage(@PathVariable("id") String id){

        HttpHeaders respHeaders = new HttpHeaders();
        try {
            
            respHeaders.set("Content-Type", "image/png");
            return ResponseEntity.ok()
                .headers(respHeaders)
                .body(corpInfoService.getLogoImage(id));

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

    @GetMapping(value = "/cpInfo/logo")
    public ResponseEntity<byte[]> getLogoImageFromToken(@RequestParam(name = "token", required = false) String token){

        HttpHeaders respHeaders = new HttpHeaders();
        
        try {

            respHeaders.set("Content-Type", "image/png");
            return ResponseEntity.ok()
                .headers(respHeaders)
                .body(corpInfoService.getLogoImageFromToken(token));

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

    @GetMapping(value = "/cpInfo/title", produces = "application/json")
    public  String getCpInfo(@RequestParam(required = false) Map<String, String> queryMap){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            JsonObject cpJsonObject = corpInfoService.getCpInfo(queryMap);

            respMsg.setResponseObject(cpJsonObject);
            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("Get cpInfo success");

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toString();
    }

    @GetMapping(value = "/cpInfo/title/{id}", produces = "application/json")
    public String getCpInfoWithId(@PathVariable("id") String id, @RequestParam(required = false) Map<String, String> queryMap){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        queryMap.put("id", id);

        try {
            JsonObject cpJsonObject = corpInfoService.getCpInfoById(queryMap);

            respMsg.setResponseObject(cpJsonObject);
            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("Get cpInfo success");

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toString();
    }
}