package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.MERCUtils;
import com.gemteks.merc.service.am.common.MERCValidator;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.model.ActionInfo;
import com.gemteks.merc.service.am.repo.mysql.ApiUserSocialMappingRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Service
public class SocialMappingService {

    @Autowired
    private MERCUtils mercUtils;
    @Autowired
    private ApiUserSocialMappingRepository apiUserSocialMappingRepository;

    public boolean updateSocialMapping(Map<String, String> parameter) throws MercException, IOException, FirebaseAuthException {

        String socialType = parameter.get("socialType");
        String socialId = parameter.get("socialId");
        String token = parameter.get("token");
        String method = parameter.get("method");

        // check token not null, history, expired
        MERCValidator.validateParam(socialType);
        MERCValidator.validateParam(socialId);
        MERCValidator.validateParam(token);
        ActionInfo actionInfo = mercUtils.checkValidToken(token);

        if (socialType.equals("firebase")){

            FileInputStream refreshToken = new FileInputStream("service-account-file.json");
            System.out.println("refreshToken: " + refreshToken);

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .setDatabaseUrl("https://merc-frontend-notify.firebaseio.com/")
                    .setProjectId("merc-frontend-notify")
                    .build();

            FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);

            UserRecord userRecord = null;
            String type = "uid";
            switch (type){
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
            if (userRecord == null) {
                throw new MercException("999", "Error fetching user data");
            }

            System.out.println("method: " + method);
            if (method.equals("POST")) {
                return apiUserSocialMappingRepository.insertUserSocialMapping(Integer.valueOf(actionInfo.getUserID()), userRecord.getProviderId(), userRecord.getUid(), new Date()) > 0;
            }
            else {
                // delete
                return apiUserSocialMappingRepository.deleteUserSocialMapping(Integer.valueOf(actionInfo.getUserID()), userRecord.getProviderId(), userRecord.getUid()) > 0;
            }
        }

        if (socialType.equals("line")){
            throw new MercException("999", "socialType line");
        }
        throw new MercException("999", "socialType error");
    }
}
