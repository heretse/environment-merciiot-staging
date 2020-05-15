package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.TripleDES;
import com.gemteks.merc.service.am.common.MERCUtils;
import com.gemteks.merc.service.am.common.MERCValidator;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.model.ActionInfo;

import com.gemteks.merc.service.am.model.mongodb.Users;
import com.gemteks.merc.service.am.model.mysql.ApiSystemProperties;
import com.gemteks.merc.service.am.model.mysql.ApiUser;
import com.gemteks.merc.service.am.repo.mongodb.UsersRepository;
import com.gemteks.merc.service.am.repo.mysql.ApiSystemPropertiesRepository;
import com.gemteks.merc.service.am.repo.mysql.ApiUserMgmRepository;
import com.gemteks.merc.service.am.repo.mysql.ApiUserRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserMgmService {

    @Value("${encrypt.secretKey}")
    private String secretKey;

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiUserRepository apiUserRepository;
    @Autowired
    private ApiUserMgmRepository apiUserMgmRepository;
    @Autowired
    private ApiSystemPropertiesRepository apiSystemPropertiesRepository;

    @Autowired
    private UsersRepository usersRepository;

    public JsonArray getUserList(String token, String search) throws MercException {

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
                System.out.println(e);
                throw new MercException("999", "search text decrypt error");
            }
        }

        // check accessFlg
        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        List userLists;

        // check dataset
        // set all user query
        if (actionInfo.getDataSet().equals("0") ){
            userLists = apiUserMgmRepository.getAllUserInfo(searchText);
        }
        // set all user by cp
        else if (actionInfo.getDataSet().equals("1")){
            userLists = apiUserMgmRepository.getAllUserInfoByCpId(searchText, actionInfo.getCpID());
        }
        else {
            throw new MercException("401", "no permission to access");
        }

        JsonArray docJsonArray = new JsonArray();

        if (userLists.size() != 0){
            for (Object userList : userLists){
                Map row = (Map) userList;
                JsonObject doc = new JsonObject();

                doc.addProperty("userId", row.get("userId") == null ? -1 : Integer.valueOf(row.get("userId").toString()));
                doc.addProperty("cpId", row.get("cpId") == null ? -1 : Integer.valueOf(row.get("cpId").toString()));
                doc.addProperty("roleId", row.get("roleId") == null ? -1 : Integer.valueOf(row.get("roleId").toString()));
                doc.addProperty("userName", row.get("userName") == null ? "NA" : row.get("userName").toString());
                doc.addProperty("pic", row.get("pic") == null ? "NA" : row.get("pic").toString());
                doc.addProperty("email", row.get("email") == null ? "NA" : row.get("email").toString());
                doc.addProperty("userBlock", row.get("userBlock") == null ? -1 : Integer.valueOf(row.get("userBlock").toString()));
                doc.addProperty("userType", row.get("userType") == null ? -1 : Integer.valueOf(row.get("userType").toString()));
                doc.addProperty("createTime", row.get("createTime") == null ? "NA" : row.get("createTime").toString());
                doc.addProperty("cpName", row.get("cpName") == null ? "NA" : row.get("cpName").toString());
                doc.addProperty("roleName", row.get("roleName") == null ? "NA" : row.get("roleName").toString());
                doc.addProperty("blockDesc", row.get("blockDesc") == null ? "NA" : row.get("blockDesc").toString());

                docJsonArray.add(doc);
            }
        }

        return docJsonArray;
    }

    public Boolean updateUserStatus(Map<String, String> parameter) throws MercException {

        boolean resetPwdFlg = false;
        String mUserId = parameter.get("mUserId");
        String catId = parameter.get("catId");
        String roleId = parameter.get("roleId");
        String userBlock = parameter.get("userBlock");
        String pwd = parameter.get("pwd");
        String token = parameter.get("token");

        // check token not null, history, expired
        MERCValidator.validateParam(mUserId);
        MERCValidator.validateParam(catId);
        MERCValidator.validateParam(roleId);
        MERCValidator.validateParam(userBlock);

        String pwdEncrypt = "";
        if (pwd == null || pwd.length() == 0) {
            resetPwdFlg = false;
        }
        else {
            resetPwdFlg = true;
            checkFlow(pwd, "PWD_FORMAT");

            // encrypt pwd
            try {
                pwdEncrypt = TripleDES.encrypt(pwd, secretKey);
            } catch (Exception e){
                throw new MercException("999", "pwd encrypt error");
            }
        }
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // check dataset
        if (!actionInfo.getDataSet().equals("0") && !actionInfo.getDataSet().equals("1")){
            throw new MercException("401", "no permission to access");
        }

        if (resetPwdFlg){
            return (apiUserRepository.updateUserStatusWithPwd(Integer.valueOf(catId), Integer.valueOf(roleId), Integer.valueOf(userBlock), new Date(), Integer.valueOf(actionInfo.getUserID()), pwdEncrypt, Integer.valueOf(mUserId)) == 1);
        }
        else {
            return (apiUserRepository.updateUserStatus(Integer.valueOf(catId), Integer.valueOf(roleId), Integer.valueOf(userBlock), new Date(), Integer.valueOf(actionInfo.getUserID()), Integer.valueOf(mUserId)) == 1);
        }
    }

    public Boolean insertUserByAdmin(Map<String, String> parameter) throws MercException {

        String catId = parameter.get("catId");
        String roleId = parameter.get("roleId");
        String userName = parameter.get("name");
        String pwd = parameter.get("pwd");
        String gender = parameter.get("gender");
        String email = parameter.get("email");
        String userBlock = parameter.get("userBlock");
        String token = parameter.get("token");

        String nickName = userName;
        Integer deviceType = 3;
        String pic = "dummy";
        Integer userType = 3;

        // check token not null, history, expired
        MERCValidator.validateParam(gender);
        MERCValidator.validateParam(catId);
        MERCValidator.validateParam(roleId);
        MERCValidator.validateParam(userBlock);
        MERCValidator.validateParam(token);

        // check flow
        checkFlow(userName, "ACC_FORMAT");
        checkFlow(email, "EMAIL_FORMAT");
        checkFlow(pwd, "PWD_FORMAT");

        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // check dataset
        if (!actionInfo.getDataSet().equals("0") && !actionInfo.getDataSet().equals("1")){
            throw new MercException("401", "no permission to access");
        }

        //check user exist
        List<ApiUser> checkUserExist = apiUserRepository.checkUserExist(Integer.valueOf(catId), userName, email);
        if (checkUserExist.size() > 0){
            throw new MercException("403", "username or email exists");
        }

        // encrypt pwd
        String pwdEncrypt = "";
        try {
            pwdEncrypt = TripleDES.encrypt(pwd, secretKey);
        } catch (Exception e){
            throw new MercException("999", "pwd encrypt error");
        }

        // set data to sql
        if (apiUserRepository.insertUserByAdmin(Integer.valueOf(catId), Integer.valueOf(roleId), userName, nickName, gender, pwdEncrypt, deviceType, pic, email, Integer.valueOf(userBlock), userType, new Date(), Integer.valueOf(actionInfo.getUserID())) == 1){
            // get insert id
            String insertId = apiUserRepository.getLastId().toString();
            System.out.println("insertId: " + insertId);

            // set to mongoDB
            usersRepository.save(new Users(insertId, insertId));

            return true;
        }
        else {
            return false;
        }
    }

    public Boolean delUser(Map<String, String> parameter) throws MercException {

        String delUserId = parameter.get("delUserId");
        String token = parameter.get("token");
        MERCValidator.validateParam(delUserId);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);
        MERCValidator.checkAccessAuthority(actionInfo.getGrp());

        // check dataset
        if (!actionInfo.getDataSet().equals("0") && !actionInfo.getDataSet().equals("1")){
            throw new MercException("401", "no permission to access");
        }

        // set data to sql
        if (actionInfo.getDataSet().equals("0")){
            if (apiUserRepository.deleteUserByUserId(Integer.valueOf(delUserId)) == 1){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (apiUserRepository.deleteUserByUserIdAndCpId(Integer.valueOf(actionInfo.getCpID()), Integer.valueOf(delUserId)) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Boolean checkFlow(String checkStr, String properties) throws MercException {

        validateParam(checkStr);
        validateParam(properties);

        List<ApiSystemProperties> systemProperties = apiSystemPropertiesRepository.findPropertiesByName(properties);

        if (systemProperties.size() == 0){
            throw new MercException("404", "Missing parameter");
        }

        // check format
        Pattern pattern = Pattern.compile(systemProperties.get(0).getPValue());
        Matcher matcher = pattern.matcher(checkStr);
        if (matcher.matches()){
            return true;
        }
        else {
            throw new MercException("404", properties + "error");
        }
    }

    public void validateParam(String param) throws MercException {

        if (param == null || param.length() == 0 || param.equals("")) {
            throw new MercException("404", "Missing parameter");
        }
    }
}
