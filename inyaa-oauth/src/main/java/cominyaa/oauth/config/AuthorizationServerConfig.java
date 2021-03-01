/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cominyaa.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lengleng
 * @date 2019/2/1 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


	@Resource
	private AuthenticationManager authenticationManager;
	@Resource
	private DataSource dataSource;
	@Resource
	private UserDetailsService userDetailsService;
	@Autowired
	protected TokenEnhancer jwtTokenEnhancer;
	@Autowired
	protected TokenStore jwtTokenStore;
	@Autowired
	protected JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired
	protected PasswordEncoder passwordEncoder;

	/**
	 * 数据库模式
	 */
	@Bean
	public TokenStore jdbcTokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		//   jwt 增强模式
		//   对令牌的增强操作就在enhance方法中
		//   面在配置类中,将TokenEnhancer和JwtAccessConverter加到一个enhancerChain中
		//
		//   通俗点讲它做了两件事：
		//   给JWT令牌中设置附加信息和jti：jwt的唯一身份标识,主要用来作为一次性token,从而回避重放攻击
		//   判断请求中是否有refreshToken,如果有,就重新设置refreshToken并加入附加信息
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		List<TokenEnhancer> enhancerList = new ArrayList<>();
		enhancerList.add(jwtTokenEnhancer);
		enhancerList.add(jwtAccessTokenConverter);
		enhancerChain.setTokenEnhancers(enhancerList); //将自定义Enhancer加入EnhancerChain的delegates数组中

		endpoints
				//设置tokenStore
				.tokenStore(jwtTokenStore)
				//支持 refresh_token 模式
				.userDetailsService(userDetailsService)
				//支持 password 模式
				.authenticationManager(authenticationManager)
				//token扩展属性
				.tokenEnhancer(enhancerChain)
				//设置token转换器
				.accessTokenConverter(jwtAccessTokenConverter)
				//设置每次通过refresh_token获取新的access_token时，是否重新生成一个新的refresh_token。
				//默认true，不重新生成
				//.reuseRefreshTokens(true)
				//设置允许处理的请求类型
				.allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
				//获取公钥的请求全部允许
				.tokenKeyAccess("permitAll()")
				//验证token有效性的请求需要先登录认证
				.checkTokenAccess("isAuthenticated()")
				//允许客户端form表单认证，就是可以将client_id和client_secret放到form中提交，否则必须使用Basic认证
				.allowFormAuthenticationForClients();
	}


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		JdbcClientDetailsService detailsService = new JdbcClientDetailsService(dataSource);
		detailsService.setPasswordEncoder(passwordEncoder);
		clients.withClientDetails(detailsService);
	}

}
