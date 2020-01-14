package com.ftwj.servicesecurity.config;

import ch.qos.logback.core.rolling.helper.TokenConverter;
import com.ftwj.servicesecurity.service.MyUserDetailService;
import com.ftwj.servicesecurity.token.CustomerTokenConverter;
import com.ftwj.servicesecurity.token.CutomerTokenEnhancer;
import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    // password 模式需要注入authenticationManager
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;


    @Autowired
    private MyUserDetailService userDetailsService;

    @Autowired
    private ClientDetailsService clientDetailsService;



    @Primary
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setClientDetailsService(clientDetailsService);
      //  tokenServices.setTokenEnhancer(tokenEnhancer());
        // 设置access_token的有效期，单位是秒。默认是12个小时
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(1));
        // 在access_token失效的情况下，用于获取新的access_token，默认是30天，10天
        //tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(REFRESH_TOKEN_EXPIRE_DAY));
        // 设置refresh token是否重复使用，true：reuse；false：no reuse.
        tokenServices.setReuseRefreshToken(false);
        return tokenServices;
    }

    // 使用数据库来保存token
    @Bean
    public TokenStore tokenStore(){
        //return new JdbcTokenStore(dataSource);
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    // 使用数据库来保存token
    @Bean
    public ClientDetailsService clientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new CutomerTokenEnhancer();
    }
    @Bean
    public DefaultAccessTokenConverter  tokenConverter(){
        return new CustomerTokenConverter();
    }
    /**
     * 配置令牌端点(Token Endpoint)的安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许表单认证
        security.allowFormAuthenticationForClients().tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    /**
     * 配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你
     * 能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),jwtAccessTokenConverter()));
        endpoints.userDetailsService(userDetailsService) // 存储用户信息
                .tokenStore(tokenStore())  // 存储令牌
                .authenticationManager(authenticationManager)// 配置授权
              //  .accessTokenConverter(tokenConverter()) // 配置自定义生成的token转换器
                .tokenEnhancer(tokenEnhancerChain);
        //.exceptionTranslator();
               // .tokenEnhancer(tokenEnhancer()); // accesstoken 返回中携带额外的用户信息

        // 强制使用SSL
        //endpoints.
        // 配置令牌服务
       // endpoints.tokenServices(tokenServices());
    }

    /**
     * 配置JwtAccessTokenConverter转换器：生成token的转换器，默认的token是有签名的，：对称和非对称两种签名方式
     * 对称加密需要授权服务器和资源服务器存储同一key值，而非对称加密可使用密钥加密，暴露公钥给资源服务器验签
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth-jwt.jks"),"yp-yl123".toCharArray());
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth-jwt"));
        return jwtAccessTokenConverter;
    }

}
