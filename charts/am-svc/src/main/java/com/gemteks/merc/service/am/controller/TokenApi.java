package com.gemteks.merc.service.am.controller;

import com.gemteks.merc.service.am.common.HttpResponseMessage;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TokenApi {
    @Autowired
    private TokenService tokenSvr;

    @GetMapping(value = "/chkToken", produces = "application/json")
    public String tokenFlow(@RequestParam(required = false) String token){
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            String tokenData = tokenSvr.checkToken(token);

            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("query success");
            respMsg.appendFieldAsString("data", tokenData);

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toString();
    }
}
