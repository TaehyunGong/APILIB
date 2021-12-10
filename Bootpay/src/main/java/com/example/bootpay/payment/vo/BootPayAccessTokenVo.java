package com.example.bootpay.payment.vo;

import lombok.Data;

@Data
public class BootPayAccessTokenVo {

    private String statue;

    private String code;

    private String message;

    private data data;

    @Data
    public class data{
        // 부트페이 REST API를 이용하기 위해 HTTP Header에 Authorization 값에 채워야하는 인증 값 입니다.
        private String token;

        // 부트페이 서버의 Unixtime입니다. ( 부트페이 서버는 ubuntu.pool.ntp.org 시간 기준으로 동기화 됩니다. )
        private String server_time;

        // Access Token이 만료되는 Unixtime입니다. ( 첫 발급된 후 30분 뒤 만료됩니다. )
        private String server_at;
    }

}
