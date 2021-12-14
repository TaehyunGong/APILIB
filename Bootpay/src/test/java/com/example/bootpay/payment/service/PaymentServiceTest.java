package com.example.bootpay.payment.service;

import com.example.bootpay.globalconfig.BootPayAccessToken;
import com.example.bootpay.payment.vo.BootPayCancelVo;
import com.example.bootpay.payment.vo.BootPayResponseVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
    Mono<BootPayResponseVo<BootPayCancelVo>> mono;

    @Test
    void paymentCancel() throws JsonProcessingException {
        // given
        String receipt_id = "temp_receipt_id";
        String accessToken = "temp_access_token";
        BootPayResponseVo<BootPayCancelVo> vo = new BootPayResponseVo<BootPayCancelVo>();
        vo.setData(new BootPayCancelVo());

        // when
        when(bootPayAccessToken.getAccessToken(any())).thenReturn(accessToken);

        // -- WebClient Mock --
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.header(anyString(), anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.bodyValue(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(new ParameterizedTypeReference<BootPayResponseVo<BootPayCancelVo>>(){})).thenReturn(mono);
        when(mono.block()).thenReturn(vo);
        // --

        // TODO: 이제 JSON으로 오류확인해야함
        BootPayCancelVo cancelVo = service.paymentCancel("");

        // then
        Assertions.assertNotNull(cancelVo);

    }

}
