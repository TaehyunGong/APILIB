package com.example.bootpay.globalconfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Component
public class BootPayAccessToken {

    // 결제 검증시 필요한 ID
    private final String REST_APPLICATION_ID;

    // 부트페이 서버 대조용 ID
    private final String REST_PRIVATE_KEY;

    // 2개의 ID를 json String 으로 변경
    private final String SECRET_JSON_KEY;

    public BootPayAccessToken(@Value("${api.bootpay.rest_applicationid}") String REST_APPLICATION_ID
                            , @Value("${api.bootpay.rest_private_key}") String REST_PRIVATE_KEY) throws JsonProcessingException {
        this.REST_APPLICATION_ID = REST_APPLICATION_ID;
        this.REST_PRIVATE_KEY = REST_PRIVATE_KEY;

        Map map = new HashMap<String, String>();
        map.put("private_key", this.REST_PRIVATE_KEY);
        map.put("application_id", this.REST_APPLICATION_ID);

        SECRET_JSON_KEY = new ObjectMapper().writer().writeValueAsString(map);
    }

    /**
     * BootPay의 결제 검증을 위하여 AccessToken을 발급받는다.
     * @param webClient
     * @return
     */
    public String getAccessToken(WebClient webClient){
        final String uri = UriComponentsBuilder.fromUriString("https://api.bootpay.co.kr")
                .path("/request/token")
                .build()
                .encode()
                .toUriString();

        return webClient.post()
                .uri(uri)
                .header("Content-Type", "application/json")
                .bodyValue(SECRET_JSON_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
