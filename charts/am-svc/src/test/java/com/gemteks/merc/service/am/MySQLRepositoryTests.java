package com.gemteks.merc.service.am;

import java.util.ArrayList;
import java.util.List;

import com.gemteks.merc.service.am.model.mysql.ApiLoginHistory;
import com.gemteks.merc.service.am.model.mysql.ApiSystemProperties;
import com.gemteks.merc.service.am.repo.mysql.ApiLoginHistoryRepository;
import com.gemteks.merc.service.am.repo.mysql.ApiSystemPropertiesRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest()
class MySQLRepositoryTests {
    @Autowired
    private ApiSystemPropertiesRepository apiSystemPropertiesRepository;

    @Autowired
    private ApiLoginHistoryRepository apiLoginHistoryRepository;

    @Test
    void testGetSystemPropertiesByName(){
        List<ApiSystemProperties> properties = apiSystemPropertiesRepository.findBypName("ACC_FORMAT");

        System.out.printf("properties size():%d\n", properties.size());
        for (ApiSystemProperties property: properties) {
			System.out.printf("property:%s\n", property.getPName());
		}

    }

    @Test
	public void getApiLogninHistoryList(){
		Iterable<ApiLoginHistory> histories= apiLoginHistoryRepository.findAll();
		List<ApiLoginHistory> historyList = new ArrayList<>();
		
		histories.forEach(historyList::add);
		for (ApiLoginHistory history: historyList) {
			System.out.printf("UserToken:%s, ip:%s\n", history.getUserToken(), history.getHistoryIp());
		}
    }

}
