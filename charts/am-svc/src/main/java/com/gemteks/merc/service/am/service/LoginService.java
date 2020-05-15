package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.*;
import com.gemteks.merc.service.am.model.mysql.ApiSystemProperties;
import com.gemteks.merc.service.am.model.mysql.projection.LoginListItem;
import com.gemteks.merc.service.am.model.mysql.projection.RaMappingListItem;
import com.gemteks.merc.service.am.repo.mysql.ApiLoginRepository;
import com.gemteks.merc.service.am.repo.mysql.ApiRoleRepository;
import com.gemteks.merc.service.am.repo.redis.RedisRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class LoginService {

    @Value("${certApi.url}")
    private String certApiUrl;
    @Value("${certCheck.url}")
    private String certCheckUrl;

    @Value("${encrypt.secretKey}")
    private String secretKey;
    @Value("${encrypt.tokenSecretKey}")
    private String tokenSecretKey;

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiLoginRepository apiLoginRepository;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private ApiRoleRepository apiRoleRepository;

    public JsonObject login(Map<String, String> parameter) throws MercException {

        // check env
        if (!System.getenv("deployEnv").equals("dev")){
            // cert flow
            JsonObject certResult = certFlow();
            if (!certResult.get("responseCode").getAsString().equals("000")){
                String responseCode = certResult.get("responseCode").getAsString();
                String responseMsg = certResult.get("responseMsg").getAsString();
                if (certResult.get("errorMsg") != null){
                    responseMsg = responseMsg + ":" + certResult.get("errorMsg").getAsString();
                }
                throw new MercException(responseCode, responseMsg);
            }
        }

        String acc = parameter.get("acc");
        String pwd = parameter.get("pwd");
        String type = parameter.get("type");
        String ipAddress = parameter.get("ipAddress");
        String cpName = parameter.get("cpName");

        // check token not null, history, expired
        MERCValidator.validateParam(acc);
        MERCValidator.validateParam(pwd);
        MERCValidator.validateParam(type);

        // check user exist or not
        List<LoginListItem> userInfo = apiLoginRepository.checkUserExist(cpName, acc);
        if (userInfo.size() == 0){
            throw new MercException("404", "No user data");
        }

        List<RaMappingListItem> userGrps = apiLoginRepository.getUserGrp(userInfo.get(0).getRoleId());

        // compare the input pwd with pwd in database
        // decrypt the pwd in database
        String pwdDecrypt = "";
        try {
            pwdDecrypt = TripleDES.decrypt(userInfo.get(0).getUserPwd(), secretKey);
        } catch (Exception e){
            throw new MercException("999", "user password decrypt error");
        }
        if (pwd.equals(pwdDecrypt)){
            if (userInfo.get(0).getUserBlock() == 1){
                throw new MercException("401", "user block");
            }
        }
        else {
            throw new MercException("403", "password incorrect");
        }

        // gen token
        Date date = new Date();
        long nowSeconds = Math.round(date.getTime()/1000);
        String grpStr = "";
        if (userGrps.size() > 0){
            for (int i = 0; i < userGrps.size(); i++){
                grpStr += userGrps.get(i).getGrpId().toString();
                if (i < (userGrps.size() - 1)){
                    grpStr += ",";
                }
            }
        }
        String token = grpStr + ":" + String.valueOf(nowSeconds) + ":" + userInfo.get(0).getUserId().toString() + ":"
                + userInfo.get(0).getCpId().toString() + ":" + userInfo.get(0).getRoleId().toString() + ":"
                + userInfo.get(0).getDataset().toString();
        System.out.println("token: " + token);

        // encrypt token
        String tokenEncrypt = "";
        try {
            tokenEncrypt = TripleDES.encrypt(token, tokenSecretKey);
        } catch (Exception e){
            throw new MercException("999", "token encrypt error");
        }

        // set to redis
        ApiSystemProperties tokenExpireProperty = apiLoginRepository.getTokenExpireProperty();
        String tokenExpireTime = tokenExpireProperty.getPValue() == null ? "86400" : tokenExpireProperty.getPValue();

        if (!redisRepository.set(tokenEncrypt, token, Integer.valueOf(tokenExpireTime))){
            throw new MercException("999", "set redis error");
        }

        // insert login history
        Date currentTime = new Date();
        JsonObject userInfoObjects = new JsonObject();
        JsonArray grpJsonArray = new JsonArray();

        if (apiLoginRepository.insertLoginHistory(userInfo.get(0).getUserId(), tokenEncrypt, currentTime, ipAddress, Integer.valueOf(type), currentTime) > 0){
            JsonObject userInfoObject = new JsonObject();
            userInfoObject.addProperty("name", userInfo.get(0).getUserName() == null ? "NA" : userInfo.get(0).getUserName());
            userInfoObject.addProperty("nickName", userInfo.get(0).getNickName() == null ? "NA" : userInfo.get(0).getNickName());
            userInfoObject.addProperty("gender", userInfo.get(0).getGender() == null ? "NA" : userInfo.get(0).getGender());
            userInfoObject.addProperty("pic", userInfo.get(0).getPic() == null ? "NA" : userInfo.get(0).getPic());
            userInfoObject.addProperty("email", userInfo.get(0).getEmail() == null ? "NA" : userInfo.get(0).getEmail());

            for (RaMappingListItem userGrp : userGrps){
                JsonObject grpObject = new JsonObject();

                grpObject.addProperty("grpId", userGrp.getGrpId() == null ? -1 : userGrp.getGrpId());
                grpObject.addProperty("grpName", userGrp.getGrpName() == null ? "NA" : userGrp.getGrpName());
                grpObject.addProperty("createFlg", userGrp.getCreateFlg() == null ? -1 : userGrp.getCreateFlg());
                grpObject.addProperty("updateFlg", userGrp.getUpdateFlg() == null ? -1 : userGrp.getUpdateFlg());
                grpObject.addProperty("deleteFlg", userGrp.getDeleteFlg() == null ? -1 : userGrp.getDeleteFlg());

                grpJsonArray.add(grpObject);
            }

            userInfoObjects.add("userInfo", userInfoObject);
            userInfoObjects.add("services", grpJsonArray);
            userInfoObjects.addProperty("role", userInfo.get(0).getRoleName());
            userInfoObjects.addProperty("userId", userInfo.get(0).getUserId().toString());
            userInfoObjects.addProperty("authToken", tokenEncrypt);
        }

        return userInfoObjects;
    }

    public JsonObject certFlow() throws MercException {

        RestTemplate restTemplate = new RestTemplate();
        String apiKeyStr = "api_key";
        String apiSecretStr = "api_secret";

        // get key
        String responseStr = restTemplate.getForObject(certApiUrl, String.class, apiKeyStr);
        JsonObject responseGet = DeserializeJson.toObject(responseStr);
        if (!responseGet.get("responseCode").getAsString().equals("000")){
            return responseGet;
        }
        String apiKey = responseGet.get("prop").getAsJsonObject().get("p_val").getAsString();
        System.out.println("apiKey: " + apiKey);

        // get secret
        responseStr = restTemplate.getForObject(certApiUrl, String.class, apiSecretStr);
        responseGet = DeserializeJson.toObject(responseStr);
        if (!responseGet.get("responseCode").getAsString().equals("000")){
            return responseGet;
        }
        String apiSecret = responseGet.get("prop").getAsJsonObject().get("p_val").getAsString();
        System.out.println("apiSecret: " + apiSecret);

        Date date = new Date();
        long nowTs = (long) Math.floor(date.getTime() / 1000);

        String apiToken = apiSecret + ":" + String.valueOf(nowTs);
        String apiTokenEncode = "";
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] textByte = apiToken.getBytes("UTF-8");
            apiTokenEncode = encoder.encodeToString(textByte);
            System.out.println("apiToken: " + apiToken);
            System.out.println("apiTokenEncode: " + apiTokenEncode);
        } catch (Exception e){
            throw new MercException("999", "cert encode error");
        }

        // post
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("api_key", apiKey);
        params.add("api_token", apiTokenEncode);
        params.add("ts", String.valueOf(nowTs));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<String> responsePost = client.exchange(certCheckUrl, HttpMethod.POST, requestEntity, String.class);
        JsonObject data = DeserializeJson.toObject(responsePost.getBody());

        return data;
    }
}
