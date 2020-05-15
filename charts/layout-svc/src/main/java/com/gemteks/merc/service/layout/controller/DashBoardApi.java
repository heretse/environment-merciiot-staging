package com.gemteks.merc.service.layout.controller;

import java.util.Map;
import com.gemteks.merc.service.layout.common.DeserializeJson;
import com.gemteks.merc.service.layout.common.HttpResponseMessage;
import com.gemteks.merc.service.layout.common.MERCUtils;
import com.gemteks.merc.service.layout.common.MERCValidator;
import com.gemteks.merc.service.layout.common.MercException;
import com.gemteks.merc.service.layout.model.ActionInfo;
import com.gemteks.merc.service.layout.service.DashBoardService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashBoardApi {
    @Autowired
    private DashBoardService dashboardService;

    @Autowired
    private MERCUtils mercUtils;

    @GetMapping(value = "/db")
    public  Map<String, Object> getDashBoardInfo(@RequestParam(required = false) String token){

        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            MERCValidator.validateParam(token);
            
            ActionInfo actionInfo = mercUtils.checkValidToken(token);
            JsonArray layoutInfo = dashboardService.getDashBoardLayoutInfo(actionInfo);

            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("query success");
            respMsg.appendField("layout", layoutInfo);
        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }

    @PostMapping(value = "/db", consumes = "application/json")
    public Map<String, Object> insertOrUpdateDashboard(@RequestBody String paramater) throws MercException {
        HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            JsonObject jobj = DeserializeJson.toObject(paramater);

            String token =  jobj.get("token").getAsString();
            JsonArray layoutList =  jobj.getAsJsonArray("layout");

            MERCValidator.validateParam(token);
            MERCValidator.validateLayout(layoutList);
            ActionInfo actionInfo = mercUtils.checkValidToken(token);
            Integer putDocStatus = dashboardService.putDashBoardLayoutInfo(actionInfo, token, layoutList);

            respMsg.addResponseCode("000");
            respMsg.addResponseMsg(putDocStatus == 1? "Insert layout success":"Update layout success");
        } catch (MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        } catch (NullPointerException e) {
            respMsg.addResponseCode("999");
            respMsg.addResponseMsg("Missing parameter");
        } catch (JsonSyntaxException e) {
            respMsg.addResponseCode("999");
            respMsg.addResponseMsg("The parameter of Json is not valided");
        }
        return respMsg.toMap();
    }

}
