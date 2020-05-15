package com.gemteks.merc.service.am.controller;

import com.gemteks.merc.service.am.common.HttpResponseMessage;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LogoutApi {
    @Autowired
    private LogoutService logoutSvr;

    @PostMapping(value = "/logout")
    public Map<String, Object> logout(@RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            if (logoutSvr.logout(parameter.get("token"))){
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("logout success");
            }
            else {
                respMsg.addResponseCode("404");
                respMsg.addResponseMsg("already logout or no login data");
            }

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }
}
