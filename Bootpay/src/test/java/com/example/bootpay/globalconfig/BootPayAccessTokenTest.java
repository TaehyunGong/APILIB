package com.example.bootpay.globalconfig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BootPayAccessTokenTest {

    @Autowired
    WebClient webClient;

    @Autowired
    BootPayAccessToken bootPayAccessToken;

    @Test
    public void accessToken_test(){
        // given

        // when
        String access_key = bootPayAccessToken.getAccessToken(webClient);

        // then
        Assertions.assertNotNull(access_key);
    }
}