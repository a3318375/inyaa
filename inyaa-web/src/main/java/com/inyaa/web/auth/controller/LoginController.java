package com.inyaa.web.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.UserInfo;
import com.inyaa.web.exception.CustomErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class LoginController {

    private final RestTemplate restTemplate;
    private final ClientRegistrationRepository clientRegistrationRepository;

    public LoginController(RestTemplateBuilder restTemplateBuilder, ClientRegistrationRepository clientRegistrationRepository) {
        this.restTemplate = restTemplateBuilder.build();
        this.restTemplate.setErrorHandler(new CustomErrorHandler());
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
        ResponseEntity<JSONObject> resp = restTemplate.getForEntity(url, JSONObject.class);
        if (resp.getStatusCodeValue() == 200) {
            return BaseResult.success(resp.getBody());
        } else {
            return BaseResult.error(resp.getStatusCodeValue(), Objects.requireNonNull(resp.getBody()).getString("error_description"));
        }
    }

    @RequestMapping("/view")
    public String view(){
        return "redirect:http://localhost:3100/#/home/welcome";
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
