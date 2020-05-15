package com.gemteks.merc.service.location.controller;

import javax.servlet.http.HttpServletRequest;
import com.gemteks.merc.service.location.common.DeserializeJson;
import com.gemteks.merc.service.location.common.HttpResponseMessage;
import com.gemteks.merc.service.location.common.MercException;
import com.gemteks.merc.service.location.service.PlanService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlanApi {
    @Autowired
    private PlanService planService;

    @GetMapping(value = "/plan", produces = "application/json")
    public  String getPlanInfo(@RequestParam(required = false) String token){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            JsonArray planList = planService.getPlanInfo(token);

            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("query success");
            respMsg.appendField("fList", planList);

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        } 

        return respMsg.toString();
    }

    @GetMapping(value = "/img")
    public ResponseEntity<byte[]> getPlanImage(@RequestParam(name = "token", required = false) String token,
                @RequestParam(name = "id", required = false) String id){

        HttpHeaders respHeaders = new HttpHeaders();
        try {
            
            respHeaders.set("Content-Type", "image/png");
            return ResponseEntity.ok()
                .headers(respHeaders)
                .body(planService.getPlanImage(token, id));
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

    @GetMapping(value = "/excel")
    public ResponseEntity<byte[]> getReportXls(@RequestParam(name = "token", required = false) String token,
                @RequestParam(name = "id", required = false) String id){

        HttpHeaders respHeaders = new HttpHeaders();
        try {
            
            respHeaders.set("Content-Type", "application/vnd.ms-excel");
            return ResponseEntity.ok()
                .headers(respHeaders)
                .body(planService.getPlanImage(token, id));
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

    @PostMapping(value = "/plan", consumes = "multipart/form-data", produces = "application/json")
    public String insertOrUpdatePlanInfo(@RequestParam("myFile") MultipartFile  file, HttpServletRequest request) {
        HttpResponseMessage respMsg = new HttpResponseMessage();
    
        try {

            Integer putDocStatus = planService.insertOrUpdatePlanInfo(file, request);
            respMsg.addResponseCode("000");
            respMsg.addResponseMsg(putDocStatus == 1? "Insert plan success":"Update plan success"); 
        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
            return respMsg.toString();
        }

        return respMsg.toString();
    }

    @DeleteMapping(value = "/plan", consumes = "application/json", produces = "application/json")
    public String deletePlanInfo(@RequestBody String paramater)  {
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            JsonObject jobj = DeserializeJson.toObject(paramater);

            String token =  jobj.get("token").getAsString();
            String id = jobj.get("id").getAsString();

            if (planService.deletePlanInfo(id, token)) {
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("Delete success");
            } else {
                respMsg.addResponseCode("999");
                respMsg.addResponseMsg("Delete fail");
            }
        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }
        return respMsg.toString();
    }

}
