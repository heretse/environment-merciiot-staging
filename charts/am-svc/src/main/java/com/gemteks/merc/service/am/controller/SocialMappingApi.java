package com.gemteks.merc.service.am.controller;

import com.gemteks.merc.service.am.common.HttpResponseMessage;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.service.SocialMappingService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class SocialMappingApi {
    @Autowired
    private SocialMappingService socialMappingSvr;

    @PostMapping(value = "/socialUpdate")
    public Map<String, Object> updateSocialMapping(HttpServletRequest request, @RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            parameter.put("method", request.getMethod());

            if (socialMappingSvr.updateSocialMapping(parameter)){
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("social account update successfully");
            }
            else {
                respMsg.addResponseCode("999");
                respMsg.addResponseMsg("social account update failed");
            }

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        } catch (FirebaseAuthException | IOException e) {
            e.printStackTrace();
        }

        return respMsg.toMap();
    }

    @DeleteMapping(value = "/socialUpdate")
    public Map<String, Object> deleteSocialMapping(HttpServletRequest request, @RequestBody Map<String, String> parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            parameter.put("method", request.getMethod());

            if (socialMappingSvr.updateSocialMapping(parameter)){
                respMsg.addResponseCode("000");
                respMsg.addResponseMsg("social account update successfully");
            }
            else {
                respMsg.addResponseCode("999");
                respMsg.addResponseMsg("social account update failed");
            }

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        } catch (FirebaseAuthException | IOException e) {
            e.printStackTrace();
        }

        return respMsg.toMap();
    }
}
