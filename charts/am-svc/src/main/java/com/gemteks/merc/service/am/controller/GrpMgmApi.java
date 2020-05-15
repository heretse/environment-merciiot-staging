package com.gemteks.merc.service.am.controller;

import com.gemteks.merc.service.am.common.HttpResponseMessage;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.service.GrpMgmService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class GrpMgmApi {
    @Autowired
    private GrpMgmService grpMgmSvr;

    @GetMapping(value = "/grp", produces = "application/json")
    public String getGrpList(@RequestParam(required = false) String token, @RequestParam(required = false) String search){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            JsonArray cpList = grpMgmSvr.getGrpList(token, search);

            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("query success");
            respMsg.appendFieldAsInteger("size", cpList.size());
            respMsg.appendField("grps", cpList);

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toString();
    }

    @PostMapping(value = "/grp")
    public Map<String, Object> insertUpdateGrp(@RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            Map<String, String> result = grpMgmSvr.insertUpdateGrp(parameter);

            if (result.get("result").equals("1")){
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg(result.get("action") + " success");
            }
            else {
                respMsg.addResponseCode("999");
                respMsg.addResponseMsg(result.get("action") + " fail");
            }

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }

    @DeleteMapping(value = "/grp")
    public Map<String, Object> delGrp(@RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();
        try {
            String result = grpMgmSvr.delGrp(parameter);
            if (result.equals("success")) {
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("delete " + result);
            }else {
                respMsg.addResponseCode("999");
                respMsg.addResponseMsg("delete " + result);
            }

        }catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }
}
