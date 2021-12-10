package com.example.bootpay.payment.controller;

import com.example.bootpay.payment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        String data = "{\"receipt_id\": \"61b31a85e38c30001f4a3d08\", \"action\": \"BootpayConfirm\"}";

        // when
        when(service.paymentConfirm(anyString())).thenReturn(true);

        // then
        mockMvc.perform(post("/paymentConfirm").content(data))
                .andExpect(content().string("true"))
                .andDo(print());

    }
}