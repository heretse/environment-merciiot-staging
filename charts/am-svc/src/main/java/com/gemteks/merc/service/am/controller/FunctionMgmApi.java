package com.gemteks.merc.service.am.controller;

import com.gemteks.merc.service.am.common.HttpResponseMessage;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.service.FunctionMgmService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FunctionMgmApi {
    @Autowired
    private FunctionMgmService functionMgmSvr;

    @GetMapping(value = "/func", produces = "application/json")
    public String getFunctionList(@RequestParam(required = false) String token, @RequestParam(required = false) String search){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            JsonArray functionList = functionMgmSvr.getFunctionList(token, search);

            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("query success");
            respMsg.appendFieldAsString("size", String.valueOf(functionList.size()));
            respMsg.appendField("funcs", functionList);

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toString();
    }

    @PostMapping(value = "/func")
    public Map<String, Object> insertUpdateFunction(@RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            Map<String, String> result = functionMgmSvr.insertUpdateFunction(parameter);

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

    @DeleteMapping(value = "/func")
    public Map<String, Object> delFunction(@RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();
        try {
            String result = functionMgmSvr.delFunction(parameter);

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
