package com.mock.auth.security.config;

import cn.hutool.core.map.MapUtil;
import com.mock.auth.security.core.user.SysUserDetails;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhao
 * @since 2022-06-15 16:11
 * @description: 认证授权配置
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService sysUserDetailServiceImpl;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 添加 OAuth2 客户端
     * @param clients
     * @throws Exception
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        log.info("------ OAuth2客户端初始化成功 ------");
        clients.inMemory()
                .withClient("zhao") // 客户端名
                .secret(passwordEncoder.encode("123456")) // 客户端密码
                .scopes("all") //授权范围
//                .resourceIds("") // 资源id
                // authorization_code授权码模式,password密码模式,implicit简化模式,client_credentials客户端模式,可以设置同时支持多种模式
                .authorizedGrantTypes("authorization_code","refresh_token","password") // 授权方式
                .accessTokenValiditySeconds(3600) // token过期时间 一个小时
                .refreshTokenValiditySeconds(7200); // refresh_token过期时间 两个小时
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        log.info("------ 配置OAuth2授权 ------");
        // JWT 内容增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(tokenEnhancer());
        delegates.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(delegates);

        // token存储模式设定 默认为InMemoryTokenStore模式存储到内存中
        // 同样要告诉spring security 去添加以下东西
        endpoints.tokenStore(tokenStore());
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain);

//                .tokenServices(tokenServices(endpoints));
    }

    /**
     * jwt token存储模式
     */
    @Bean
    public TokenStore tokenStore(){
        log.info("------ token存储模式 ------");
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        KeyPair keyPair = factory.getKeyPair("jwt", "123456".toCharArray());
        return keyPair;
    }

    /**
     * jwt内容增强
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                Map<String, Object> additionalInfo = MapUtil.newHashMap();
                Object principal = authentication.getUserAuthentication().getPrincipal();
                if (principal instanceof SysUserDetails) {
                    SysUserDetails sysUserDetails = (SysUserDetails) principal;
                    additionalInfo.put("userId", sysUserDetails.getUserId());
                    additionalInfo.put("username", sysUserDetails.getUsername());
                    additionalInfo.put("authorities", sysUserDetails.getAuthorities());
//                    additionalInfo.put("deptId", sysUserDetails.getDeptId());
                    // 认证身份标识(username:用户名；)
//                    if (StrUtil.isNotBlank(sysUserDetails.getAuthenticationIdentity())) {
//                        additionalInfo.put("authenticationIdentity", sysUserDetails.getAuthenticationIdentity());
//                    }
                }
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
                return accessToken;
            }
        };
    }

}
