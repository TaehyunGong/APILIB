package com.example.bootpay.payment.service;

import com.example.bootpay.globalconfig.BootPayAccessToken;
import com.example.bootpay.payment.vo.BootPayCancelVo;
import com.example.bootpay.payment.vo.BootPayResponseVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

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

    // https://docs.bootpay.co.kr/rest/cancel
    public BootPayCancelVo paymentCancel(String receipt_id) throws JsonProcessingException {
        final String accessToken = this.accessToken.getAccessToken(webClient);

        // URI 생성 - https://api.bootpay.co.kr/cancel
        String uri = UriComponentsBuilder.fromUriString("https://api.bootpay.co.kr")
                .path("/cancel")
                .build()
                .encode()
                .toUriString();

        // BodyValue 전송을 위한 JSON 문자열로 변경
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("receipt_id", receipt_id);
        node.put("name", "이름");
        node.put("reason", "사유");

        String jsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);

        // cancel REST 전송 후 HashMap 타입으로 변환하여 응답 받음
        BootPayResponseVo<BootPayCancelVo> responseVo = webClient.post()
                .uri(uri)
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonBody)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BootPayResponseVo<BootPayCancelVo>>(){})
                .block();

        // info 로그 출력
        logger.info(responseVo.getData().toString());

        // 반환
        return responseVo.getData();
    }
}

