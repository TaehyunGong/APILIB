package com.example.bootpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class BootpayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootpayApplication.class, args);
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

}
