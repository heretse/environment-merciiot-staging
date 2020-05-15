package com.gemteks.merc.service.layout.repo.redis;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RedisRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Get value by key to Redis.
     * @param key 
     * @return
     */
    public Object get(String key){
        if (key == null) {
            return null;
        }

        try {
            return redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Set value by key to Redis
     * @param key
     * @param value
     * @return
     */
    public Boolean set(String key, Object value) {
        
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 
     * Set value with expired time by key to Redis
     * @param key
     * @param value
     * @param time as expire time
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {

            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete key from Redis.
     * @param key
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

}