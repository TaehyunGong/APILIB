package com.example.bootpay.payment.service;

import com.example.bootpay.globalconfig.BootPayAccessToken;
import com.example.bootpay.payment.vo.BootPayCancelVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService service;

    @Mock
    BootPayAccessToken bootPayAccessToken;

    @Mock//(answer = Answers.RETURNS_DEEP_STUBS)
    WebClient webClient;

    @Mock
    WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    WebClient.ResponseSpec responseSpec;
    @Mock
    Mono mono;

    @Test
    void paymentCancel() throws JsonProcessingException {
        // given
        String receipt_id = "temp_receipt_id";
        String accessToken = "temp_access_token";
        HashMap hashMap = new HashMap();
        hashMap.put("data", "{}");

        // when
        when(bootPayAccessToken.getAccessToken(any())).thenReturn(accessToken);

        // -- WebClient Mock --
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.header(anyString(), anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(HashMap.class)).thenReturn(mono);
        when(mono.block()).thenReturn(hashMap);
        // --

        // TODO: 이제 JSON으로 오류확인해야함
        BootPayCancelVo cancelVo = service.paymentCancel("");

        // then
        Assertions.assertNotNull(cancelVo);

    }
}
