package com.gemteks.merc.service.am.controller;

import com.gemteks.merc.service.am.common.HttpResponseMessage;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.service.MappingMgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MappingMgmApi {
    @Autowired
    private MappingMgmService mappingMgmSvr;

    @PostMapping(value = "/mapping")
    public Map<String, Object> updateMapping(@RequestBody String parameter) {

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            Map<String, String> result = mappingMgmSvr.updateMapping(parameter);

            respMsg.addResponseCode(result.get("responseCode"));
            respMsg.addResponseMsg(result.get("responseMsg"));

        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }
}
