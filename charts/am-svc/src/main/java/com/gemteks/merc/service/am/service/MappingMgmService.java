package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.DeserializeJson;
import com.gemteks.merc.service.am.common.MERCUtils;
import com.gemteks.merc.service.am.common.MERCValidator;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.model.ActionInfo;
import com.gemteks.merc.service.am.repo.mysql.ApiMappingRepository;
import com.gemteks.merc.service.am.repo.mysql.ApiMappingRollBackRepository;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MappingMgmService {

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiMappingRepository apiMappingRepository;
    @Autowired
    private ApiMappingRollBackRepository apiMappingRollBackRepository;

    public Map<String, String> updateMapping(String parameter) throws MercException {

        JsonObject parameterObject = DeserializeJson.toObject(parameter);

        String catId = parameterObject.get("catId") == null ? "":parameterObject.get("catId").getAsString();
        String type = parameterObject.get("type") == null ? "":parameterObject.get("type").getAsString();
        String token = parameterObject.get("token") == null ? "":parameterObject.get("token").getAsString();

        // check token not null, history, expired
        MERCValidator.validateParam(catId);
        MERCValidator.validateParam(type);
        MERCValidator.validateParam(token);

        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        // check accessFlg
        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        Map<String, String> resultMap = new HashMap<>();

        // check users, func, grps null or not
        JsonArray dataArray;

        if (type.equals("CP") || type.equals("Role") || type.equals("Egrp")){
            dataArray = parameterObject.getAsJsonArray("users") == null ? new JsonArray() : parameterObject.getAsJsonArray("users");
        }
        else if(type.equals("Func") || type.equals("GFunc")){
            dataArray = parameterObject.getAsJsonArray("func") == null ? new JsonArray() : parameterObject.getAsJsonArray("func");
        }
        else if (type.equals("GCP") || type.equals("GRole")){
            dataArray = parameterObject.getAsJsonArray("grps") == null ? new JsonArray() : parameterObject.getAsJsonArray("grps");
        }
        else {
            throw new MercException("404", "unknown type");
        }

        int dataArraySize = dataArray.size();
        ArrayList<Integer> idList = new ArrayList<Integer>();
        for (int i = 0; i < dataArraySize; i++){
            idList.add(dataArray.get(i).getAsJsonObject().get("id").getAsInt());
        }
        System.out.println("idList: " + idList);

        if (dataArraySize > 0){
            // have data to update
            if (type.equals("CP") || type.equals("Role") || type.equals("GFunc")){

                boolean resultFlg = false;

                try {
                    if (type.equals("CP")) {
                        if (apiMappingRollBackRepository.updateUserCpId(new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(catId), idList) > 0) {
                            resultFlg = true;
                        }
                    }
                    if (type.equals("Role")) {
                        if (apiMappingRollBackRepository.updateUserRoleIdByRoleId(new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(catId), idList) > 0) {
                            resultFlg = true;
                        }
                    }
                    if (type.equals("GFunc")) {
                        if (apiMappingRollBackRepository.updateFunctionMappingGrpId(new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(catId), idList) > 0) {
                            resultFlg = true;
                        }
                    }
                } catch (Exception e) {
                    throw new MercException("404", "SQL error");
                }

                // set result
                if (resultFlg){
                    resultMap.put("responseCode", "000");
                    resultMap.put("responseMsg", "update success");
                }
                else {
                    resultMap.put("responseCode", "999");
                    resultMap.put("responseMsg", "update fail");
                }
            }
            if (type.equals("Egrp") || type.equals("Func") || type.equals("GCP") || type.equals("GRole")){
                int affectedRow = 0;
                int affectedRowCount = 0;
                String title = "";
                ArrayList<String> errorMsg = new ArrayList<String>();

                // delete
                if (type.equals("Egrp")) {
                    title = "User";
                    affectedRow = apiMappingRepository.deleteUserMappingByLoIdAndUserId(Integer.valueOf(catId), idList);
                }
                if (type.equals("Func")) {
                    title = "Function";
                    affectedRow = apiMappingRepository.deleteFunctionMappingByRoleIdAndFunctionId(Integer.valueOf(catId), idList);
                }
                if (type.equals("GCP")) {
                    title = "GRole";
                    affectedRow = apiMappingRepository.deleteCpMappingByCpIdAndGrpId(Integer.valueOf(catId), idList);
                }
                if (type.equals("GRole")) {
                    title = "GRole";
                    affectedRow = apiMappingRepository.deleteRaMappingByRoleIdAndGrpId(Integer.valueOf(catId), idList);
                }

                // split
                for (int i = 0; i < dataArraySize; i++) {
                    JsonObject data = dataArray.get(i).getAsJsonObject();

                    // update
                    if (type.equals("Egrp")) {
                        title = "User";
                        affectedRow = apiMappingRepository.updateUserMappingLocIdByUserId(Integer.valueOf(catId), new Date(),
                                Integer.valueOf(actionInfo.getUserID()), data.get("id").getAsInt());
                    }
                    if (type.equals("Func")) {
                        title = "Function";
                        affectedRow = apiMappingRepository.updateFunctionMappingByRoleIdAndFunctionId(new Date(), Integer.valueOf(actionInfo.getUserID()),
                                Integer.valueOf(catId), data.get("id").getAsInt());
                    }
                    if (type.equals("GCP")) {
                        title = "GRole";
                        affectedRow = apiMappingRepository.updateCpMappingByCpIdAndGrpId(new Date(), Integer.valueOf(actionInfo.getUserID()),
                                Integer.valueOf(catId), data.get("id").getAsInt());
                    }
                    if (type.equals("GRole")) {
                        title = "GRole";
                        affectedRow = apiMappingRepository.updateRaMappingByRoleIdAndGrpId(data.get("createFlg").getAsInt(), data.get("updateFlg").getAsInt(),
                                data.get("deleteFlg").getAsInt(), data.get("sortId").getAsInt(), new Date(),
                                Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(catId), data.get("id").getAsInt());
                    }

                    // update error, then insert
                    if (affectedRow == 0){
                        if (type.equals("Egrp")) {
                            affectedRow = apiMappingRepository.insertUserMapping(data.get("id").getAsInt(), Integer.valueOf(catId), new Date(),
                                    Integer.valueOf(actionInfo.getUserID()));
                        }
                        if (type.equals("Func")) {
                            affectedRow = apiMappingRepository.insertFunctionMapping(Integer.valueOf(catId), data.get("id").getAsInt(),
                                    new Date(), Integer.valueOf(actionInfo.getUserID()));
                        }
                        if (type.equals("GCP")) {
                            affectedRow = apiMappingRepository.insertCpMapping(Integer.valueOf(catId), data.get("id").getAsInt(),
                                    new Date(), Integer.valueOf(actionInfo.getUserID()));
                        }
                        if (type.equals("GRole")) {
                            affectedRow = apiMappingRepository.insertRaMapping(Integer.valueOf(catId), data.get("id").getAsInt(),
                                    data.get("sortId").getAsInt(), data.get("createFlg").getAsInt(), data.get("updateFlg").getAsInt(),
                                    data.get("deleteFlg").getAsInt(),  new Date(), Integer.valueOf(actionInfo.getUserID()));
                        }
                    }

                    // check update or insert success or not
                    if (affectedRow > 0){
                        affectedRowCount++;
                    }
                    else {
                        errorMsg.add("update " + title + " : " + dataArray.get(i).getAsJsonObject().get("id") + " fail");
                    }
                }

                // set result
                if (dataArraySize == affectedRowCount){
                    resultMap.put("responseCode", "000");
                    resultMap.put("responseMsg", "update success");
                    return resultMap;
                }
                else {
                    resultMap.put("responseCode", "999");
                    resultMap.put("responseMsg", String.join(", ", errorMsg));
                    return resultMap;
                }
            }
        }
        else {
            // no data to update
            if (type.equals("CP") || type.equals("Role") || type.equals("Egrp")){
                // remove
                boolean resultFlg = false;
                if (type.equals("CP")){
                    if (apiMappingRepository.updateUserCpIdByCpId(new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(catId)) > 0){
                        resultFlg = true;
                    }
                }
                if (type.equals("Role")){
                    if (apiMappingRepository.updateUserRoleIdByRoleId(new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(catId)) > 0){
                        resultFlg = true;
                    }
                }
                if (type.equals("Egrp")){
                    if (apiMappingRepository.deleteUserMappingByLocId(Integer.valueOf(catId)) > 0){
                        resultFlg = true;
                    }
                }

                // set result
                if (resultFlg){
                    resultMap.put("responseCode", "000");
                    resultMap.put("responseMsg", "update success");
                }
                else {
                    resultMap.put("responseCode", "999");
                    resultMap.put("responseMsg", "update fail");
                }
            }
            if (type.equals("Func")){
                resultMap.put("responseCode", "401");
                resultMap.put("responseMsg", "Function in role can not be empty");
            }
            if (type.equals("GFunc")){
                resultMap.put("responseCode", "401");
                resultMap.put("responseMsg", "Func in grp can not be empty");
            }
            if (type.equals("GCP")){
                resultMap.put("responseCode", "401");
                resultMap.put("responseMsg", "Grp in cp can not be empty");
            }
            if (type.equals("GRole")){
                resultMap.put("responseCode", "401");
                resultMap.put("responseMsg", "Grp in role can not be empty");
            }
        }

        return resultMap;
    }
}
