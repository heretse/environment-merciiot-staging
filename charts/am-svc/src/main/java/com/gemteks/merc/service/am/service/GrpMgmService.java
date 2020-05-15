package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.MERCUtils;
import com.gemteks.merc.service.am.common.MERCValidator;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.model.ActionInfo;
import com.gemteks.merc.service.am.model.mysql.ApiGrp;
import com.gemteks.merc.service.am.model.mysql.projection.GrpListItem;
import com.gemteks.merc.service.am.repo.mysql.ApiGrpRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GrpMgmService {

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiGrpRepository apiGrpRepository;

    public JsonArray getGrpList(String token, String search) throws MercException {

        // check token not null, history, expired
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        List<GrpListItem> grpLists;
        if (search == null || search.length() == 0){
            search = "";
        }
        // check role
        if (actionInfo.getRoleID().equals("1")){
            // set all grp query
            grpLists = apiGrpRepository.getAllGrp(search);
        }
        else {
            // check accessFlg
            MERCValidator.checkAccessAuthority(actionInfo.getGrp());
            //set grp query
            grpLists = apiGrpRepository.getGrpByCpId(Integer.valueOf(actionInfo.getCpID()), search);
        }

        JsonArray grpJsonArray = new JsonArray();

        if (grpLists.size() != 0){
            for (GrpListItem grpList : grpLists){
                JsonObject grpObject = new JsonObject();

                grpObject.addProperty("grpId", grpList.getGrpId() == null ? -1 : grpList.getGrpId());
                grpObject.addProperty("grpName", grpList.getGrpName() == null ? "NA" : grpList.getGrpName());
                grpObject.addProperty("createTime", grpList.getCreateTime() == null ? "NA" : grpList.getCreateTime().toString());

                grpJsonArray.add(grpObject);
            }
        }

        return grpJsonArray;
    }

    public Map<String, String> insertUpdateGrp(Map<String, String> parameter) throws MercException {

        String grpId = parameter.get("grpId");
        String grpName = parameter.get("name");
        String token = parameter.get("token");

        // check token not null, history, expired
        MERCValidator.validateParam(grpId);
        MERCValidator.validateParam(grpName);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        if (!actionInfo.getRoleID().equals("1")){
            throw new MercException("401", "no permission to access");
        }

        // check grpName exist or not
        List<ApiGrp> grpLists = apiGrpRepository.getAllGrpByGrpName(grpName);

        Map<String, String> resultMap = new HashMap<>();

        if (grpId.equals("-1")){
            // if name exist when insert, then throw exception
            if (grpLists.size() > 0){
                throw new MercException("401", "grp name already exist");
            }

            resultMap.put("action", "insert");
            if (actionInfo.getRoleID().equals("1")){
                // set all grp query
                resultMap.put("result", String.valueOf(apiGrpRepository.insertGrp(grpName, new Date(), Integer.valueOf(actionInfo.getUserID()))));
            }
            else {
                throw new MercException("401", "no permission to access");
            }
        }
        else {
            // if name exist and id is not same as input, then throw exception
            if (grpLists.size() > 0 && !grpLists.get(0).getGrpId().equals(Integer.valueOf(grpId))){
                throw new MercException("401", "grp name already exist");
            }

            resultMap.put("action", "update");
            resultMap.put("result", String.valueOf(apiGrpRepository.updateGrpByGrpId(grpName, new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(grpId))));
        }

        return resultMap;
    }

    public String delGrp(Map<String, String> parameter) throws MercException {

        String grpId = parameter.get("grpId");
        String token = parameter.get("token");
        MERCValidator.validateParam(grpId);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        // check role
        if (!actionInfo.getRoleID().equals("1")){
            throw new MercException("401", "no permission to access");
        }

        // delete grp
        if (apiGrpRepository.deleteGrpByGrpId(Integer.valueOf(grpId)) == 1){
            apiGrpRepository.deleteRaMappingByGrpId(Integer.valueOf(grpId));

            return "success";
        }
        else {
            return "fail grp";
        }
    }
}
