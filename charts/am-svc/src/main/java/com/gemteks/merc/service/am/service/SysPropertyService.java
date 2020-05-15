package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.MERCUtils;
import com.gemteks.merc.service.am.common.MERCValidator;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.model.ActionInfo;
import com.gemteks.merc.service.am.model.mysql.ApiSystemProperties;
import com.gemteks.merc.service.am.model.mysql.projection.SysPropertyListItem;
import com.gemteks.merc.service.am.repo.mysql.ApiSystemPropertiesRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysPropertyService {

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiSystemPropertiesRepository apiSystemPropertiesRepository;

    public JsonArray getSysPropertyList(String token, String search) throws MercException {

        // check token not null, history, expired
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        // check accessFlg
        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        String searchText = (search == null || search.length() == 0) ? "" : search;
        List<String> grpLists = Arrays.asList(actionInfo.getGrp().split(","));
        List<SysPropertyListItem> sysPropertyLists = apiSystemPropertiesRepository.getAllSysProperty(grpLists, searchText);

        JsonArray sysPropertyJsonArray = new JsonArray();

        if (sysPropertyLists.size() > 0){
            for (SysPropertyListItem sysPropertyList : sysPropertyLists) {
                JsonObject sysProObject = new JsonObject();
                sysProObject.addProperty("p_name", sysPropertyList.getP_name() == null ? "NA" : sysPropertyList.getP_name());
                sysProObject.addProperty("p_value", sysPropertyList.getP_value() == null ? "NA" : sysPropertyList.getP_value());
                sysProObject.addProperty("p_desc", sysPropertyList.getP_desc() == null ? "NA" : sysPropertyList.getP_desc());
                sysProObject.addProperty("p_type", sysPropertyList.getP_type() == null ? "NA" : sysPropertyList.getP_type());
                sysProObject.addProperty("createTime", sysPropertyList.getCreateTime() == null ? "NA" : sysPropertyList.getCreateTime().toString());

                sysPropertyJsonArray.add(sysProObject);
            }
        }

        return sysPropertyJsonArray;
    }

    public Map<String, String> insertUpdateSysProperty(Map<String, String> parameter) throws MercException {

        String sysId = parameter.get("sysId");
        String p_name = parameter.get("name");
        String p_value = parameter.get("value");
        String p_desc = parameter.get("desc");
        String p_type = parameter.get("type");
        String token = parameter.get("token");

        // check token not null, history, expired
        MERCValidator.validateParam(sysId);
        MERCValidator.validateParam(p_name);
        MERCValidator.validateParam(p_value);
        MERCValidator.validateParam(p_desc);
        MERCValidator.validateParam(p_type);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // check roleName exist or not
        List<ApiSystemProperties> sysPropertyLists = apiSystemPropertiesRepository.findPropertiesByName(p_name);

        Map<String, String> resultMap = new HashMap<>();

        if (sysId.equals("-1")){
            // if name exist when insert, then throw exception
            if (sysPropertyLists.size() > 0){
                throw new MercException("401", "properties name already exist");
            }

            resultMap.put("action", "insert");
            resultMap.put("result", String.valueOf(apiSystemPropertiesRepository.insertSysProperty(p_name, p_value, p_desc, p_type, new Date(), Integer.valueOf(actionInfo.getUserID()))));
        }
        else {
            resultMap.put("action", "update");
            resultMap.put("result", String.valueOf(apiSystemPropertiesRepository.updateSysPropertyByPName(p_value, p_desc, p_type, new Date(), Integer.valueOf(actionInfo.getUserID()), p_name)));
        }

        return resultMap;
    }

    public String delSysProperty(Map<String, String> parameter) throws MercException {

        String p_name = parameter.get("name");
        String token = parameter.get("token");
        MERCValidator.validateParam(p_name);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // delete sysProperty
        if (apiSystemPropertiesRepository.deleteSysPropertyByPName(p_name) == 1){
            return "success";
        }
        else {
            return "fail sysProperty";
        }
    }
}
