package com.gemteks.merc.service.am;

import java.util.List;
import java.util.Map;

import com.gemteks.merc.service.am.model.mongodb.Maps;
import com.gemteks.merc.service.am.model.mongodb.State;
import com.gemteks.merc.service.am.repo.mongodb.MapsRepository;
import com.gemteks.merc.service.am.repo.mongodb.StateRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest()
class MongoDBTests {

    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
	private MapsRepository mapsRepository;

    @Test 
	public void testStateRepositoryForMongoDB(){
		List<State> stateList = stateRepository.findAll();
		System.out.printf("stateList's number:%d\n", stateList.size());

		for (State state: stateList) {
			System.out.printf("id:%s\n", state.getId());
			System.out.printf("macaddress:%s\n", state.getMacAddr());
			Map<String, String> info = state.getInformation();
			System.out.printf("--information--\n");
			System.out.printf("status:%d\n", Integer.valueOf(info.get("status")));

			Map<String, String> pre_info = state.getPreInformation();
			System.out.printf("--pre_information--\n");
			System.out.printf("status:%d\n", Integer.valueOf(pre_info.get("status")));	
			
			Map<String, Object> extra = state.getExtra();

			Integer rssi = (Integer)extra.get("rssi");
			System.out.printf("rssi:%d\n", rssi);	
			Double snr_min = (Double)extra.get("snr_min");
			System.out.printf("snr_min:%f\n", snr_min);	
		}
    }
    
    @Test 
	public void testMapsRepository(){
		List<Maps> mapList = mapsRepository.findAll();


		System.out.printf("Maps size:%d\n", mapList.size());

		for (Maps map: mapList){
			System.out.printf("Map's map size:%d\n", map.getValidator().size());
			for (Map<String, Object>  validator: map.getValidator()){
				String checkField = (String)validator.get("checkField");
				String type = (String)validator.get("type");
				String val = (String)validator.get("val");
				System.out.printf("checkField:%s, type:%s, val:%s\n", checkField, type, val);

			}

		}
	}
}