package com.example.bootpay.payment.controller;

import com.example.bootpay.payment.service.PaymentService;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @MockBean
    PaymentService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    void paymentConfirm() throws Exception {
        // given
        String data = "61b434d0d3d0570021f9e4ab";

        // when
        when(service.paymentConfirm(anyString())).thenReturn(true);

        // then
        mockMvc.perform(post("/paymentConfirm")
                        .contentType(MediaType.TEXT_PLAIN_VALUE)
                        .content(data))
                .andExpect(content().string("true"))
                .andDo(print());

    }
}