package com.gemteks.merc.service.am.controller;

import com.gemteks.merc.service.am.common.HttpResponseMessage;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.service.CpMgmService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CpMgmApi {
    @Autowired
    private CpMgmService cpMgmSvr;

    @GetMapping(value = "/cps", produces = "application/json")
    public String getCpList(@RequestParam(required = false) String token, @RequestParam(required = false) String search){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            JsonArray cpList = cpMgmSvr.getCpList(token, search);

            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("query success");
            respMsg.appendFieldAsInteger("size", cpList.size());
            respMsg.appendField("cps", cpList);

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toString();
    }

    @PostMapping(value = "/cps")
    public Map<String, Object> insertUpdateCp(@RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            Map<String, String> result = cpMgmSvr.insertUpdateCp(parameter);

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

    @DeleteMapping(value = "/cps")
    public Map<String, Object> delCp(@RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();
        try {

            if (cpMgmSvr.delCp(parameter)) {
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
}
