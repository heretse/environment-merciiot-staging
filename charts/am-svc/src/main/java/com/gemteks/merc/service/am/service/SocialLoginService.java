package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.*;
import com.gemteks.merc.service.am.model.mysql.ApiSystemProperties;
import com.gemteks.merc.service.am.model.mysql.projection.LoginListItem;
import com.gemteks.merc.service.am.model.mysql.projection.RaMappingListItem;
import com.gemteks.merc.service.am.repo.mysql.ApiLoginRepository;
import com.gemteks.merc.service.am.repo.mysql.ApiUserSocialMappingRepository;
import com.gemteks.merc.service.am.repo.redis.RedisRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SocialLoginService {

    @Value("${encrypt.tokenSecretKey}")
    private String tokenSecretKey;

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiUserSocialMappingRepository apiUserSocialMappingRepository;
    @Autowired
    private ApiLoginRepository apiLoginRepository;
    @Autowired
    private RedisRepository redisRepository;

    public JsonObject socialLogin(Map<String, String> parameter) throws MercException {

        String cpName = parameter.get("cpName");
        String socialType = parameter.get("socialType");
        String socialId = parameter.get("socialId");
        String ipAddress = parameter.get("ipAddress");
        Integer type = 0;

        // check token not null, history, expired
        MERCValidator.validateParam(cpName);
        MERCValidator.validateParam(socialType);
        MERCValidator.validateParam(socialId);

        if (socialType.equals("firebase")){

            UserRecord userRecord = null;
            FirebaseApp firebaseApp = null;

            try {
                byte[] configByte = Files.readAllBytes(Paths.get("src/main/resources/service-account-file.json"));
                String inputConfig = new String(configByte);
                JsonObject inputConfigObject = DeserializeJson.toObject(inputConfig);

                InputStream configStream = new ByteArrayInputStream(configByte);
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(configStream))
                        .setDatabaseUrl("https://" + inputConfigObject.get("project_id").getAsString() + ".firebaseio.com/")
                        .setProjectId(inputConfigObject.get("project_id").getAsString())
                        .build();
                firebaseApp = FirebaseApp.initializeApp(options);

                String firebaseType = "uid";
                switch (firebaseType) {
                    case "uid":
                        userRecord = FirebaseAuth.getInstance(firebaseApp).getUser(socialId);
                        break;
                    case "email":
                        userRecord = FirebaseAuth.getInstance(firebaseApp).getUserByEmail(socialId);
                        break;
                    case "phone":
                        userRecord = FirebaseAuth.getInstance(firebaseApp).getUserByPhoneNumber(socialId);
                        break;
                    default:
                        break;
                }

                firebaseApp.delete();
                configStream.close();

            } catch (IOException e) {
                throw new MercException("999", " read json file error");
            } catch (FirebaseAuthException e) {
                throw new MercException("999", "firebase error");
            }

            if (userRecord == null){
                throw new MercException("999", "Error fetching user data");
            }

            List<LoginListItem> userInfo = apiUserSocialMappingRepository.getUserSocialMapping(cpName, userRecord.getProviderId(), userRecord.getUid());
            if (userInfo.size() <= 0){
                throw new MercException("404", "No user data");
            }

            List<RaMappingListItem> userGrps = apiUserSocialMappingRepository.getUserGrp(userInfo.get(0).getRoleId());

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
                System.out.println(e);
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

            if (apiLoginRepository.insertLoginHistory(userInfo.get(0).getUserId(), tokenEncrypt, currentTime, ipAddress, type, currentTime) > 0){
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
                userInfoObjects.addProperty("authToken", tokenEncrypt);
            }

            return userInfoObjects;
        }

        if (socialType.equals("line")){
            throw new MercException("999", "socialType line");
        }
        throw new MercException("999", "socialType error");
    }
}
