package com.example.bootpay.payment.controller;

import com.example.bootpay.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping(value = "/paymentConfirm")
    public @ResponseBody boolean paymentConfirm(@RequestBody Map<String, String> data){
        return service.paymentConfirm(data.get("receipt_id"));
    }

}