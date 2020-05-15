package com.gemteks.merc.service.layout.repo;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

import com.gemteks.merc.service.layout.common.MercException;
import com.gemteks.merc.service.layout.model.ActionInfo;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AmMicroService{
    private static final Integer AM_SVC_PORT = 1880;
    private static final String AM_SVC_HOST_NAME = "am-svc";

    public static ActionInfo getDataFromAmServiceWithToken(String token) throws MercException {
        try {
            String enc_token = URLEncoder.encode(token, "UTF-8");
            RestTemplate restTemplate = new RestTemplate();

            String am_svr_url = String.format("http://%s:%d/chkToken?token=%s", AmMicroService.AM_SVC_HOST_NAME, AmMicroService.AM_SVC_PORT, enc_token);
            ResponseEntity<String> response = restTemplate.getForEntity(URI.create(am_svr_url), String.class);
            System.out.printf("response:%s\n", response.getBody());
            if (response.getStatusCodeValue() == 200 ) {
                JsonParser parser = JsonParserFactory.getJsonParser();

                Map<String, Object> paramMap = parser.parseMap(response.getBody());

                if (paramMap.isEmpty()) {
                    throw new MercException("404", "Parameter not find");
                }

                Object responseCodeObject= paramMap.get("responseCode");
                if (responseCodeObject.getClass().getSimpleName().equals("Integer")) {
                    Integer respCode = (Integer)responseCodeObject;
                    if (respCode != 0) {
                        throw new MercException(String.valueOf(respCode), 
                            (String)paramMap.get("responseMsg"));
                    }
                }else if (responseCodeObject.getClass().getSimpleName().equals("String")){
                    String respCode = (String)responseCodeObject;
                    if (!respCode.equals("000")){
                        throw new MercException(respCode, 
                                (String)paramMap.get("responseMsg"));
                    }
                }

                String data = (String)paramMap.get("data");
                String[] dataList = data.split(":");

                if (dataList.length != 6) {
                    throw new MercException("999", "Token length error");
                }
                return new ActionInfo(dataList[0], dataList[1], dataList[2], 
                        dataList[3], dataList[4], dataList[5]);
            } else {
                throw new MercException("999", "Token has been expired");
            }
        } catch (UnsupportedEncodingException e) {
            throw new MercException("999", "Encode token error");
        } catch (MercException e) {
            throw new MercException(e.getCode(), e.getMessage());

        }
    }
}