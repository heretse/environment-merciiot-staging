package com.gemteks.merc.service.am.service;

import com.gemteks.merc.service.am.common.TripleDES;
import com.gemteks.merc.service.am.common.MERCValidator;
import com.gemteks.merc.service.am.common.MercException;
import com.gemteks.merc.service.am.repo.mysql.ApiLogoutRepository;
import com.gemteks.merc.service.am.repo.redis.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogoutService {

    @Value("${encrypt.tokenSecretKey}")
    private String tokenSecretKey;

    @Autowired
    private ApiLogoutRepository apiLogoutRepository;
    @Autowired
    private RedisRepository redisRepository;

    public boolean logout(String token) throws MercException {

        MERCValidator.validateParam(token);
        System.out.println(token);
        // decrypt token
        String tokenDecrypt = "";
        try {
            tokenDecrypt = TripleDES.decrypt(token, tokenSecretKey);
        } catch (Exception e){
            throw new MercException("999", "token decrypt error");
        }

        String[] dataList = tokenDecrypt.split(":");
        if (dataList.length != 6) {
            throw new MercException("999", "token fail");
        }

        // delete info in redis
        redisRepository.del(token);

        // set logout in history
        return apiLogoutRepository.updateLogoutInfo(new Date(), token, Integer.valueOf(dataList[2])) > 0;
    }
}
