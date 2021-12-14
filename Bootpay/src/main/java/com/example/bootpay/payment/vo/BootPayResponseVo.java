package com.example.bootpay.payment.vo;

import lombok.Data;

@Data
public class BootPayResponseVo<T> {
    private String statue;

    private String code;

    private String message;

    private T data;

}
