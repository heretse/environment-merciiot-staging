package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.MERCUtils;
import com.gemteks.merc.service.am.common.MERCValidator;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.model.ActionInfo;
import com.gemteks.merc.service.am.model.mysql.ApiRole;
import com.gemteks.merc.service.am.model.mysql.projection.RaMappingListItem;
import com.gemteks.merc.service.am.model.mysql.projection.RoleListItem;
import com.gemteks.merc.service.am.repo.mysql.ApiRoleRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleMgmService {

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiRoleRepository apiRoleRepository;

    public JsonArray getRoleList(String token, String search) throws MercException {

        // check token not null, history, expired
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        // check accessFlg
        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        String searchText = (search == null || search.length() == 0) ? "" : search;
        List<RoleListItem> roleLists = apiRoleRepository.getAllRole(Integer.valueOf(actionInfo.getCpID()), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(actionInfo.getUserID()), searchText);

        JsonArray roleJsonArray = new JsonArray();
        

        if (roleLists.size() > 0){
            for (RoleListItem roleList : roleLists) {
                JsonObject roleObject = new JsonObject();
                JsonArray grpJsonArray = new JsonArray();
                roleObject.addProperty("roleId", roleList.getRoleId() == null ? -1 : roleList.getRoleId());
                roleObject.addProperty("roleName", roleList.getRoleName() == null ? "NA" : roleList.getRoleName());
                roleObject.addProperty("dataset", roleList.getDataset() == null ? -1 : roleList.getDataset());
                roleObject.addProperty("editFlg", roleList.getEditFlg() == null ? -1 : roleList.getEditFlg());
                roleObject.addProperty("delFlg", roleList.getDelFlg() == null ? -1 : roleList.getDelFlg());
                roleObject.addProperty("createTime", roleList.getCreateTime() == null ? "NA" : roleList.getCreateTime().toString());

                List<RaMappingListItem> raMappingLists = apiRoleRepository.getRaMappingByRoleId(roleList.getRoleId());
                if (raMappingLists.size() != 0) {
                    for (RaMappingListItem raMappingList : raMappingLists) {
                        JsonObject grpObject = new JsonObject();
                        grpObject.addProperty("grpId", raMappingList.getGrpId() == null ? -1 : raMappingList.getGrpId());
                        grpObject.addProperty("grpName", raMappingList.getGrpName() == null ? "NA" : raMappingList.getGrpName());
                        grpObject.addProperty("createTime", raMappingList.getCreateTime() == null ? "NA" : raMappingList.getCreateTime().toString());
                        grpObject.addProperty("createFlg", raMappingList.getCreateFlg() == null ? -1 : raMappingList.getCreateFlg());
                        grpObject.addProperty("updateFlg", raMappingList.getUpdateFlg() == null ? -1 : raMappingList.getUpdateFlg());
                        grpObject.addProperty("deleteFlg", raMappingList.getDeleteFlg() == null ? -1 : raMappingList.getDeleteFlg());
                        grpObject.addProperty("sortId", raMappingList.getSortId() == null ? -1 : raMappingList.getSortId());

                        grpJsonArray.add(grpObject);
                    }
                }
                roleObject.add("grps", grpJsonArray);

                roleJsonArray.add(roleObject);
            }
        }

        return roleJsonArray;
    }

    public Map<String, String> insertUpdateRole(Map<String, String> parameter) throws MercException {

        String roleId = parameter.get("roleId");
        String roleName = parameter.get("roleName");
        String dataId = parameter.get("dataId");
        String token = parameter.get("token");

        // check token not null, history, expired
        MERCValidator.validateParam(roleId);
        MERCValidator.validateParam(roleName);
        MERCValidator.validateParam(dataId);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // check roleName exist or not
        List<ApiRole> roleLists = apiRoleRepository.getAllRoleByRoleName(roleName);

        Map<String, String> resultMap = new HashMap<>();

        if (roleId.equals("-1")){
            // if name exist when insert, then throw exception
            if (roleLists.size() > 0){
                throw new MercException("401", "role name already exist");
            }

            Integer editFlg = parameter.get("editFlg") == null ? 1 : Integer.valueOf(parameter.get("editFlg"));
            Integer delFlg = parameter.get("delFlg") == null ? 1 : Integer.valueOf(parameter.get("delFlg"));
            resultMap.put("action", "insert");
            resultMap.put("result", String.valueOf(apiRoleRepository.insertRole(roleName, Integer.valueOf(dataId), editFlg, delFlg, new Date(), Integer.valueOf(actionInfo.getUserID()))));
        }
        else {
            // if name exist and id is not same as input, then throw exception
            if (roleLists.size() > 0 && !roleLists.get(0).getRoleId().equals(Integer.valueOf(roleId))){
                throw new MercException("401", "role name already exist");
            }

            resultMap.put("action", "update");
            resultMap.put("result", String.valueOf(apiRoleRepository.updateRoleByRoleId(roleName, Integer.valueOf(dataId), new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(roleId))));
        }

        return resultMap;
    }

    public String delRole(Map<String, String> parameter) throws MercException {

        String roleId = parameter.get("roleId");
        String token = parameter.get("token");
        MERCValidator.validateParam(roleId);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // check user exist
        if (apiRoleRepository.countRoleByRoleId(Integer.valueOf(roleId)) > 0){
            throw new MercException("401", "user exist error");
        }

        // delete role
        if (apiRoleRepository.deleteRoleByRoleId(Integer.valueOf(roleId)) == 1){
            apiRoleRepository.deleteFunctionMappingByRoleId(Integer.valueOf(roleId));
            apiRoleRepository.deleteRaMappingByRoleId(Integer.valueOf(roleId));

            return "success";
        }
        else {
            return "fail role";
        }
    }
}
