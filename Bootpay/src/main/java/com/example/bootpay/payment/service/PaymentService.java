package com.example.bootpay.payment.service;

import com.example.bootpay.globalconfig.BootPayAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

}
