package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.MERCUtils;
import com.gemteks.merc.service.am.common.MERCValidator;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.model.ActionInfo;
import com.gemteks.merc.service.am.model.mysql.ApiCp;
import com.gemteks.merc.service.am.model.mysql.projection.CpListItem;
import com.gemteks.merc.service.am.model.mysql.projection.GrpListItem;
import com.gemteks.merc.service.am.repo.mysql.ApiCpRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CpMgmService {

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiCpRepository apiCpRepository;

    public JsonArray getCpList(String token, String search) throws MercException {

        // check token not null, history, expired
        MERCValidator.validateParam(token);
        Boolean sysFlg = token.equalsIgnoreCase("system_trigger");
        ActionInfo actionInfo = null;
        if(!sysFlg){
          actionInfo = mercUtils.checkValidToken(token);
        }
        
        List<CpListItem> cpLists;
        // check role
        if (sysFlg || actionInfo.getRoleID().equals("1")){
            // set all cp query
            cpLists = apiCpRepository.getAllCp((search == null || search.length() == 0) ? "" : search);
        }
        else {
            // check accessFlg
            MERCValidator.checkAccessAuthority(actionInfo.getGrp());
            //set cp query
            cpLists = apiCpRepository.getAllCpByCpId(Integer.valueOf(actionInfo.getCpID()));
        }

        JsonArray cpJsonArray = new JsonArray();
        JsonArray grpJsonArray = new JsonArray();

        if (cpLists.size() != 0){
            for (CpListItem cpList : cpLists){
                JsonObject cpObject = new JsonObject();

                cpObject.addProperty("cpId", cpList.getCpId() == null ? -1 : cpList.getCpId());
                cpObject.addProperty("cpName", cpList.getCpName() == null ? "NA" : cpList.getCpName());
                cpObject.addProperty("createTime", cpList.getCreateTime() == null ? "NA" : cpList.getCreateTime().toString());

                List<GrpListItem> grpLists = apiCpRepository.getGrpByCpId(cpList.getCpId());
                if (grpLists.size() != 0){
                    for (GrpListItem grpList : grpLists){
                        JsonObject grpObject = new JsonObject();

                        grpObject.addProperty("grpId", grpList.getGrpId() == null ? -1 : grpList.getGrpId());
                        grpObject.addProperty("grpName", grpList.getGrpName() == null ? "NA" : grpList.getGrpName());
                        grpObject.addProperty("createTime", grpList.getCreateTime() == null ? "NA" : grpList.getCreateTime().toString());

                        grpJsonArray.add(grpObject);
                    }
                }
                cpObject.add("grps", grpJsonArray);

                cpJsonArray.add(cpObject);
            }
        }

        return cpJsonArray;
    }

    public Map<String, String> insertUpdateCp(Map<String, String> parameter) throws MercException {

        String cpId = parameter.get("cpId");
        String cpName = parameter.get("cpName");
        String token = parameter.get("token");

        // check token not null, history, expired
        MERCValidator.validateParam(cpId);
        MERCValidator.validateParam(cpName);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // check cpName exist or not
        List<ApiCp> cpLists = apiCpRepository.getAllCpByCpName(cpName);

        Map<String, String> resultMap = new HashMap<>();

        if (cpId.equals("-1")){
            // if name exist when insert, then throw exception
            if (cpLists.size() > 0){
                throw new MercException("401", "cp name already exist");
            }

            resultMap.put("action", "insert");
            if (actionInfo.getRoleID().equals("1")){
                // set all cp query
                resultMap.put("result", String.valueOf(apiCpRepository.insertCp(cpName, new Date(), Integer.valueOf(actionInfo.getUserID()))));
            }
            else {
                throw new MercException("401", "no permission to access");
            }
        }
        else {
            // if name exist and id is not same as input, then throw exception
            if (cpLists.size() > 0 && !cpLists.get(0).getCpId().equals(Integer.valueOf(cpId))){
                throw new MercException("401", "cp name already exist");
            }

            resultMap.put("action", "update");
            resultMap.put("result", String.valueOf(apiCpRepository.updateCpByCpId(cpName, new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(cpId))));
        }

        return resultMap;
    }

    public Boolean delCp(Map<String, String> parameter) throws MercException {

        String cpId = parameter.get("cpId");
        String token = parameter.get("token");
        MERCValidator.validateParam(cpId);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        // check role
        if (!actionInfo.getRoleID().equals("1")){
            throw new MercException("401", "no permission to access");
        }

        // check user exist
        if (apiCpRepository.countCpByCpId(Integer.valueOf(cpId)) > 0){
            throw new MercException("401", "user exist error");
        }

        // delete cp
        if (apiCpRepository.deleteCpByCpId(Integer.valueOf(cpId)) == 1){
            return true;
        }
        else {
            return false;
        }
    }
}
