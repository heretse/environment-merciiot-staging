package com.gemteks.merc.service.am.controller;

import com.gemteks.merc.service.am.common.HttpResponseMessage;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.service.SocialLoginService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class SocialLoginApi {
    @Autowired
    private SocialLoginService socialLoginSvr;

    @PostMapping(value = "/socialLogin/{cp}")
    public Map<String, Object> socialLogin(HttpServletRequest request, @RequestBody Map<String, String> parameter, @PathVariable("cp") String cp) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            parameter.put("ipAddress", request.getRemoteAddr());
            parameter.put("cpName", cp);
            JsonObject result = socialLoginSvr.socialLogin(parameter);

            if (result.size() > 0){
                respMsg.appendField("userInfo", result.get("userInfo"));
                respMsg.appendField("services", result.get("services"));
                respMsg.appendField("role", result.get("role"));
                respMsg.appendField("authToken", result.get("authToken"));
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("login success");
            }
            else {
                respMsg.addResponseCode("999");
                respMsg.addResponseMsg("insert history fail");
            }

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }
}
