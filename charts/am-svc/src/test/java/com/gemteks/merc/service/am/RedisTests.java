package com.gemteks.merc.service.am;

import com.gemteks.merc.service.am.repo.redis.RedisRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
class RedisTests {
    
    @Autowired
    private RedisRepository redisRepository;

    @Test
	public void testGetValueFromRedis(){
		String token = "12345";
		Object value = redisRepository.get(token);

		System.out.printf("data value:%s\n", (value == null)?null:value.toString());
	}

	@Test
	public void testSetValueToRedis(){
		String token = "12345";

		Boolean value = redisRepository.set(token, "abcde", 86448);

		System.out.printf("It is %s to set value to redis\n", value.toString());

	}

	@Test
	public void testDeleteKeyFromRedis(){
		String token = "12345";

		redisRepository.del(token);


	}
}