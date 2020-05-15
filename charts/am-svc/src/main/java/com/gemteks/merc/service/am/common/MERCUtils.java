package com.gemteks.merc.service.am.common;

import java.util.List;

import com.gemteks.merc.service.am.model.ActionInfo;
import com.gemteks.merc.service.am.model.mysql.ApiLoginHistory;
import com.gemteks.merc.service.am.repo.mysql.ApiLoginHistoryRepository;
import com.gemteks.merc.service.am.repo.redis.RedisRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MERCUtils {
    @Autowired
    private RedisRepository redisRepo;

    @Autowired
    private ApiLoginHistoryRepository apiLoginHistoryRepo;

    public ActionInfo checkValidToken(String userToken) throws MercException {

        if (userToken == null || userToken.length() == 0) {
            throw new MercException("999", "missing parameters");
        }
        
        //Check valid user token
        List<ApiLoginHistory> loginHistories =  apiLoginHistoryRepo.findLoginToken(userToken);

        if (loginHistories.size() == 0) {
            throw new MercException("404", "User already logout");
        }

        //Get the data of the token from the Reids
        String tokenData = (String)redisRepo.get(loginHistories.get(0).getUserToken());

        if  (tokenData == null || tokenData.length() == 0) {
            throw new MercException("401", "Token expired");
        }

        String[] dataList = tokenData.split(":");

        if (dataList.length != 6) {
            throw new MercException("999", "Token length error");
        }
        return new ActionInfo(dataList[0], dataList[1], dataList[2], 
                        dataList[3], dataList[4], dataList[5]);

    }
}