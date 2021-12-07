package com.example.naver.login;

import com.example.naver.login.vo.NaverLoginProfileResponse;
import com.example.naver.login.vo.NaverLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Controller
public class NaverLogin {

    @Autowired
    private WebClient webClient;

    @Value("${api.naver.client_id}")
    private String client_id;

    @Value("${api.naver.client_secret}")
    private String client_secret;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/NaverLoginCallback")
    public @ResponseBody String NaverLoginCallback(@RequestParam Map<String, String> resValue){

        // ----- Access_token 발급 -----
        final String uri = UriComponentsBuilder
                            .fromUriString("https://nid.naver.com")
                            .path("/oauth2.0/token")
                            .queryParam("grant_type", "authorization_code")
                            .queryParam("client_id", this.client_id)
                            .queryParam("client_secret", this.client_secret)
                            .queryParam("code", resValue.get("code"))
                            .queryParam("state", resValue.get("state"))
                            .build()
                            .encode()
                            .toUriString();

        final NaverLoginVo naverLoginVo = webClient
                                            .get()
                                            .uri(uri)
                                            .retrieve()
                                            .bodyToMono(NaverLoginVo.class)
                                            .block();
        // ----------------------------

        // ----- 프로필 API 호출 (Unique한 id 값을 가져오기 위함) -----
        final String profileUri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/nid/me")
                .build()
                .encode()
                .toUriString();

        final NaverLoginProfileResponse profileResponse = webClient
                .get()
                .uri(profileUri)
                .header("Authorization", "Bearer " + naverLoginVo.getAccess_token())
                .retrieve()
                .bodyToMono(NaverLoginProfileResponse.class)
                .block();

        return profileResponse.getResponse().toString();
    }

}
