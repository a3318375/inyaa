package com.inyaa.web.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.inyaa.base.bean.BaseResult;
import com.inyaa.web.auth.bean.UserInfo;
import com.inyaa.web.auth.service.AuthUserService;
import com.inyaa.web.auth.vo.AuthUserVO;
import com.inyaa.web.auth.vo.UserVo;
import com.inyaa.web.exception.AjaxResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.*;

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
    @Value("${oauth2.resource.url}")
    private String resourceUrl;
    @Value("${oauth2.server.client-id}")
    private String clientId;
    @Value("${oauth2.server.client-secret}")
    private String clientSecret;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResult<JSONObject> login(@RequestBody UserInfo req) {
        String url = "%s/oauth/token?username=%s&password=%s&grant_type=password&client_id=%s&client_secret=%s";
        url = String.format(url, authMainUrl, req.getUsername(), req.getPassword(), clientId, clientSecret);
        ResponseEntity<JSONObject> resp = restTemplate.getForEntity(url, JSONObject.class);
        if (resp.getStatusCodeValue() == 200) {
            return BaseResult.success(resp.getBody());
        } else {
            return BaseResult.error(resp.getStatusCodeValue(), Objects.requireNonNull(resp.getBody()).getString("error_description"));
        }
    }

    @RequestMapping("/github/info")
    public BaseResult<AuthUserVO> userInfo(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultOAuth2User) {
            String username = ((DefaultOAuth2User) principal).getName();
            return authUserService.getUserInfo(username);
        } else {
            return BaseResult.error();
        }
    }

    @RequestMapping("/test/info")
    public BaseResult<AuthUserVO> test(Principal principal) {
        String username = principal.getName();
        return authUserService.getUserInfo(username);
    }

    @RequestMapping("/info")
    public BaseResult<AuthUserVO> userInfo() {
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<JSONObject> resp = restTemplate.exchange(resourceUrl + "/userInfo", HttpMethod.GET, new HttpEntity<String>(headers), JSONObject.class);
        if (resp.getStatusCodeValue() == 200) {
            String username = Objects.requireNonNull(resp.getBody()).getString("username");
            return authUserService.getUserInfo(username);
        } else {
            return BaseResult.error();
        }
    }


    @GetMapping("/list")
    public BaseResult<List<UserVo>> list(UserInfo userInfo) {
        return authUserService.list(userInfo);
    }

    @PostMapping("/save")
    public BaseResult<String> save(@RequestBody UserInfo userInfo) {
        return authUserService.save(userInfo);
    }

    @PostMapping("/delete")
    public BaseResult<String> delete(@RequestBody UserInfo userInfo) {
        return authUserService.delete(userInfo);
    }
    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
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

    @PostMapping("/admin/register")
    public BaseResult<String> registerAdminByGithub(@RequestBody UserInfo userInfo) {
        return authUserService.register(userInfo);
    }

    /**
     * 效果同/user
     */
    @RequestMapping(value = "/user2")
    public AjaxResponse user2(Authentication authentication) {
        return AjaxResponse.success(authentication);
    }

    /**
     * 只打印用户属性信息，由于user-info-uri指定的接口中返回了自定义扩展属性，所以这里可以获取到扩展属性
     */
    @RequestMapping(value = "/user3")
    public AjaxResponse user3(Principal principal) {
        Map<String, Object> attributes = null;
        //返回扩展属性
        if (principal instanceof OAuth2AuthenticationToken) {
            attributes = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes();
            attributes.forEach((k, v) -> System.out.println(k + "=" + v));
        }
        return AjaxResponse.success(attributes);
    }

    /**
     * 只打印用户权限信息
     */
    @RequestMapping(value = "/user4")
    public AjaxResponse user4(Principal principal) {
        Collection<? extends GrantedAuthority> authorities = null;
        //返回权限信息
        if (principal instanceof OAuth2AuthenticationToken) {
            authorities = ((OAuth2AuthenticationToken) principal).getPrincipal().getAuthorities();
            authorities.forEach(s -> System.out.println(s.getAuthority()));
        }
        return AjaxResponse.success(authorities);
    }

    @RequestMapping(value = "/user5")
    public AjaxResponse user5(Principal principal) {
        //principal在经过security拦截后，是org.springframework.security.authentication.UsernamePasswordAuthenticationToken
        //在经OAuth2拦截后，是OAuth2Authentication
        return AjaxResponse.success(principal);
    }


    @GetMapping("/token")
    public AjaxResponse token(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        return AjaxResponse.success(authorizedClient.getAccessToken().getTokenValue());
    }
}
