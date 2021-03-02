package com.inyaa.web.auth.controller;

import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.UserInfo;
import com.inyaa.web.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    private final RestTemplate restTemplate;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public LoginController(RestTemplateBuilder restTemplateBuilder, ClientRegistrationRepository clientRegistrationRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
            }
        });
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Value("${oauth2.server.url}")
    private String authMainUrl;
    @Value("${oauth2.server.client-id}")
    private String clientId;
    @Value("${oauth2.server.client-secret}")
    private String clientSecret;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResult login(@RequestBody UserInfo req) {
        String url = "%s/oauth/token?username=%s&password=%s&grant_type=password&client_id=%s&client_secret=%s";
        url = String.format(url, authMainUrl, req.getUsername(), req.getPassword(), clientId, clientSecret);
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<Object> resp = restTemplate.getForEntity(url, Object.class);
        if (resp.getStatusCodeValue() == 200) {
            return BaseResult.success(resp.getBody());
        } else {
            return BaseResult.error(resp.getStatusCodeValue(), resp.getBody().toString());
        }
    }

    /**
     * 自定义登录页面，多租户登录时先显示该页面，由用户选择要使用的认证服务
     *
     * @param model
     * @return
     */
    @RequestMapping("/oauth/login")
    public String login(Model model) {
        Map<String, String> map = new HashMap<>();
        if (clientRegistrationRepository instanceof InMemoryClientRegistrationRepository) {
            ((InMemoryClientRegistrationRepository) clientRegistrationRepository).forEach(registrations -> {
                String registrationId = registrations.getRegistrationId();
                String clientName = registrations.getClientName();
                map.put(registrationId, clientName);
            });
        }
        model.addAttribute("registrations", map);
        return "oauth2/login";
    }
}
