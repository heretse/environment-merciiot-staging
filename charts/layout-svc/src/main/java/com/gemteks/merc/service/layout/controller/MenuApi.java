package com.gemteks.merc.service.layout.controller;

import java.util.Map;
import com.gemteks.merc.service.layout.common.HttpResponseMessage;
import com.gemteks.merc.service.layout.common.MERCUtils;
import com.gemteks.merc.service.layout.common.MERCValidator;
import com.gemteks.merc.service.layout.common.MercException;
import com.gemteks.merc.service.layout.model.ActionInfo;
import com.gemteks.merc.service.layout.service.DashBoardService;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MenuApi {
    @Autowired
    private DashBoardService dashboardService;

    @Autowired
    private MERCUtils mercUtils;

    @GetMapping(value = "/menu/{id}")
    public Map<String, Object> getMenuInfo(@PathVariable("id") final Integer id,@RequestParam(required = false) final String token){
        final HttpResponseMessage respMsg = new HttpResponseMessage();

        try {
            MERCValidator.validateParam(token);
            final ActionInfo actionInfo = mercUtils.checkValidToken(token);
            final JsonArray jsonMenuItems = dashboardService.getMenuItemList(id, Integer.valueOf(actionInfo.getRoleID()));
            respMsg.addResponseCode("000");
            respMsg.addResponseMsg("get menu success");
            respMsg.appendField("func", jsonMenuItems);
        } catch (final MercException e) {
            respMsg.addResponseCode(e.getCode());
            respMsg.addResponseMsg(e.getMessage());
        }

        return respMsg.toMap();
    }
}