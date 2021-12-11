package com.example.bootpay.payment.controller;

import com.example.bootpay.payment.service.PaymentService;
import com.example.bootpay.payment.vo.BootPayCancelVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping(value = "/paymentConfirm")
    public @ResponseBody boolean paymentConfirm(@RequestBody String receipt_id){
        return service.paymentConfirm(receipt_id);
    }

    @PostMapping(value = "/paymentCancel")
    public @ResponseBody BootPayCancelVo paymentCancel(@RequestBody String receipt_id) throws JsonProcessingException {
        return service.paymentCancel(receipt_id);
    }
}