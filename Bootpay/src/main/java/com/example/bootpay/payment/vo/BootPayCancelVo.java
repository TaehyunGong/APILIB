package com.example.bootpay.payment.vo;

import lombok.Data;

@Data
public class BootPayCancelVo {

    private String receipt_id;
    private String request_cancel_price;
    private String remain_price;
    private String remain_tax_free;
    private String cancelled_price;
    private String cancelled_tax_free;
    private String revoked_at;
    private String tid;

}
