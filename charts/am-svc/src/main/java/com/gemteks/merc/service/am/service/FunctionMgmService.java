package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.TripleDES;
import com.gemteks.merc.service.am.common.MERCUtils;
import com.gemteks.merc.service.am.common.MERCValidator;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.model.ActionInfo;
import com.gemteks.merc.service.am.model.mysql.ApiFunction;
import com.gemteks.merc.service.am.repo.mysql.ApiFunctionMgmRepository;
import com.gemteks.merc.service.am.repo.mysql.ApiFunctionRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FunctionMgmService {

    @Value("${encrypt.secretKey}")
    private String secretKey;

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiFunctionRepository apiFunctionRepository;
    @Autowired
    private ApiFunctionMgmRepository apiFunctionMgmRepository;

    public JsonArray getFunctionList(String token, String search) throws MercException {

        // check token not null, history, expired
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        // check search
        String searchText = "";
        if (search == null || search.length() == 0){
            searchText = "";
        }
        else {
            // decrypt
            try {
                searchText = TripleDES.decrypt(search, secretKey);
            } catch (Exception e){
                throw new MercException("999", "search text decrypt error");
            }
        }

        // check accessFlg
        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // set all user query
        List functionLists = apiFunctionMgmRepository.getAllFunctionInfo(searchText);

        JsonArray docJsonArray = new JsonArray();

        if (functionLists.size() != 0){
            for (Object functionList : functionLists){
                Map row = (Map) functionList;
                JsonObject doc = new JsonObject();

                doc.addProperty("functionId", row.get("functionId") == null ? -1 : Integer.valueOf(row.get("functionId").toString()));
                doc.addProperty("functionName", row.get("functionName") == null ? "NA" : row.get("functionName").toString());
                doc.addProperty("functionUrl", row.get("functionUrl") == null ? "NA" : row.get("functionUrl").toString());
                doc.addProperty("parentId", row.get("parentId") == null ? -1 : Integer.valueOf(row.get("parentId").toString()));
                doc.addProperty("sortId", row.get("sortId") == null ? -1 : Integer.valueOf(row.get("sortId").toString()));
                doc.addProperty("hiddenFlg", row.get("hiddenFlg") == null ? "NA" : row.get("hiddenFlg").toString());
                doc.addProperty("delFlg", row.get("delFlg") == null ? -1 : Integer.valueOf(row.get("delFlg").toString()));
                doc.addProperty("createTime", row.get("createTime") == null ? "NA" : row.get("sortId").toString());
                doc.addProperty("grpId", row.get("grpId") == null ? -1 : Integer.valueOf(row.get("grpId").toString()));

                docJsonArray.add(doc);
            }
        }

        return docJsonArray;
    }

    public Map<String, String> insertUpdateFunction(Map<String, String> parameter) throws MercException {

        String funcId = parameter.get("funcId");
        String funcName = parameter.get("funcName");
        String funcUrl = parameter.get("funcUrl");
        String parentId = parameter.get("parentId");
        String sortId = parameter.get("sortId");
        String grpId = parameter.get("grpId");
        String hiddenFlg = parameter.get("hiddenFlg");
        String token = parameter.get("token");

        // check token not null, history, expired
        MERCValidator.validateParam(funcId);
        MERCValidator.validateParam(funcName);
        MERCValidator.validateParam(funcUrl);
        MERCValidator.validateParam(parentId);
        MERCValidator.validateParam(sortId);
        MERCValidator.validateParam(grpId);
        MERCValidator.validateParam(hiddenFlg);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // check funcName exist or not
        List<ApiFunction> functionLists = apiFunctionRepository.getAllFunctionByFunctionName(funcName);

        Map<String, String> resultMap = new HashMap<>();

        if (funcId.equals("-1")){
            // if name exist when insert, then throw exception
            if (functionLists.size() > 0){
                throw new MercException("401", "function name already exist");
            }
            Integer delFlg = parameter.get("delFlg") == null ? 1 : Integer.valueOf(parameter.get("delFlg"));
            resultMap.put("action", "insert");
            resultMap.put("result", String.valueOf(apiFunctionRepository.insertFunction(funcName, funcUrl, Integer.valueOf(parentId),
                    Integer.valueOf(sortId), hiddenFlg, delFlg, Integer.valueOf(grpId), new Date(), Integer.valueOf(actionInfo.getUserID()))));
        }
        else {
            // if name exist and id is not same as input, then throw exception
            if (functionLists.size() > 0 && !functionLists.get(0).getFunctionId().equals(Integer.valueOf(funcId))){
                throw new MercException("401", "function name already exist");
            }
            resultMap.put("action", "update");
            resultMap.put("result", String.valueOf(apiFunctionRepository.updateFunctionByFunctionId(funcName, funcUrl, Integer.valueOf(parentId),
                    Integer.valueOf(sortId), hiddenFlg, Integer.valueOf(grpId), new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(funcId))));
        }

        return resultMap;
    }

    public String delFunction(Map<String, String> parameter) throws MercException {

        String functionId = parameter.get("funcId");
        String token = parameter.get("token");
        MERCValidator.validateParam(functionId);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // delete function
        List<Integer> grpIdCollection = new ArrayList<>();
        for (String grpId : actionInfo.getGrp().split(",")){
            grpIdCollection.add(Integer.valueOf(grpId));
        }
        if (apiFunctionRepository.deleteFunctionByFunctionId(grpIdCollection, Integer.valueOf(functionId)) == 1){
            apiFunctionRepository.deleteFunctionMappingByFunctionId(Integer.valueOf(functionId));

            return "success";
        }
        else {
            return "fail function";
        }
    }
}
