package com.inyaa.web.auth.controller;

import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.UserInfo;
import com.inyaa.web.auth.service.AuthUserService;
import com.inyaa.web.auth.vo.AuthUserVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * @author byteblogs
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/user")
public class AuthUserController {

    private final RestTemplate restTemplate;
    private final AuthUserService authUserService;

    public AuthUserController(RestTemplateBuilder restTemplateBuilder, AuthUserService authUserService) {
        this.restTemplate = restTemplateBuilder.build();
        this.authUserService = authUserService;
    }

    @Value("${oauth2.server.url}")
    private String authMainUrl;
    @Value("${oauth2.server.client-id}")
    private String clientId;
    @Value("${oauth2.server.client-secret}")
    private String clientSecret;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map login(@RequestBody UserInfo req) {
        String url = "%s/oauth/token?username=%s&password=%s&grant_type=password&client_id=%s&client_secret=%s";
        url = String.format(url, authMainUrl, req.getUsername(), req.getPassword(), clientId, clientSecret);
        return restTemplate.getForObject(url, Map.class);
    }

    @RequestMapping("/info")
    public BaseResult<AuthUserVO> userInfo(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultOAuth2User) {
            String username = ((DefaultOAuth2User) principal).getName();
            return authUserService.getUserInfo(username);
        } else {
            return BaseResult.error();
        }
    }

    @GetMapping("/user")
    public Map<String, Object> user (@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @DeleteMapping("/delete/{id}")
    public BaseResult<String> deleteUser(@PathVariable Integer id) {
        return authUserService.deleteUsers(id);
    }


    @GetMapping("/master/v1/get")
    public BaseResult<AuthUserVO> getMasterUserInfo() {
        return authUserService.getMasterUserInfo();
    }

    @GetMapping("/user/list")
    public BaseResult<Page<UserInfo>> getUserList(AuthUserVO authUserVO) {
        return authUserService.getUserList(authUserVO);
    }

    @GetMapping("/github/get")
    public BaseResult<String> oauthLoginByGithub() {
        //TODO github授权 后续处理
        return BaseResult.error();
    }

    @PostMapping("/github/login")
    public BaseResult<String> saveUserByGithub(@RequestBody AuthUserVO authUserVO) {
        //TODO github登陆 后续处理
        return BaseResult.error();
    }

    @PostMapping("/admin/register")
    public BaseResult<String> registerAdminByGithub(@RequestBody UserInfo userInfo) {
        return authUserService.register(userInfo);
    }

}
