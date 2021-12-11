package com.example.bootpay.payment.service;

import com.example.bootpay.globalconfig.BootPayAccessToken;
import com.example.bootpay.payment.vo.BootPayCancelVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    WebClient webClient;

    @Autowired
    BootPayAccessToken accessToken;

    public boolean paymentConfirm(String receipt_id){
        final String accessToken = this.accessToken.getAccessToken(webClient);

        final String uri = UriComponentsBuilder.fromUriString("https://api.bootpay.co.kr")
                .path("/receipt/")
                .path(receipt_id)
                .build()
                .encode()
                .toUriString();

        final Map responseMap = webClient.get()
                .uri(uri)
                .header("Authorization", accessToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        logger.info(responseMap.toString());

        return "200".equals(String.valueOf(responseMap.get("status")));
    }

    public BootPayCancelVo paymentCancel(String receipt_id) throws JsonProcessingException {
        final String accessToken = this.accessToken.getAccessToken(webClient);

        String uri = UriComponentsBuilder.fromUriString("ttps://api.bootpay.co.kr")
                .path("/cancel")
                .build()
                .encode()
                .toUriString();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("receipt_id", receipt_id);
        node.put("name", "이름");
        node.put("reason", "사유");

        String jsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);

        // TODO: jsonBody 데이터의 encoding 문제가 있는것 같음.
        HashMap hashMap = webClient.post()
                .uri(uri)
                .header("Authorization", accessToken)
                .bodyValue(jsonBody)
                .retrieve()
                .bodyToMono(HashMap.class)
                .block();

        BootPayCancelVo cancelVo = objectMapper.readValue(hashMap.get("data").toString(), BootPayCancelVo.class);

        logger.info(cancelVo.toString());

        return cancelVo;
    }

}
