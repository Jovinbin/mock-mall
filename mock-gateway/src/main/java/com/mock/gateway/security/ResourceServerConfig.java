//package com.mock.gateway.security;
//
//import cn.hutool.core.codec.Base64;
//import cn.hutool.core.convert.Convert;
//import cn.hutool.core.io.IoUtil;
//import com.mock.common.constant.SecurityConstants;
//import com.mock.common.result.ResultCode;
//import com.mock.gateway.common.WhiteUrlInfo;
//import com.mock.gateway.config.WhiteConfig;
//import com.mock.gateway.util.ResponseUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
//import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
//import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
//import reactor.core.publisher.Mono;
//
//import java.io.InputStream;
//import java.security.KeyFactory;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author zhao
// * @since 2022-06-16 14:05
// */
//@Slf4j
//@ConfigurationProperties(prefix = "security")
//@RequiredArgsConstructor
//@Configuration
//@EnableWebFluxSecurity
//public class ResourceServerConfig {
//
//    private final ResourceServerManager resourceServerManager;
//
//    private final WhiteConfig whiteConfig;
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//
//        List<WhiteUrlInfo> whiteInfo = whiteConfig.getWhiteInfo();
//        List<String> ignoreUrls = whiteInfo.stream().map(WhiteUrlInfo::getUrls).collect(Collectors.toList());
//
//        if (ignoreUrls == null) {
//            log.error("????????????????????????????????????Nacos ???????????????????????????????????????????????????");
//        }
//
//        http.oauth2ResourceServer().jwt()
//                .jwtAuthenticationConverter(jwtAuthenticationConverter())
////                .publicKey(rsaPublicKey())   // ??????????????????
//        //.jwkSetUri()  // ????????????????????????????????????key???spring.security.oauth2.resourceserver.jwt.jwk-set-uri
//        ;
//        http.oauth2ResourceServer().authenticationEntryPoint(authenticationEntryPoint());
//        http.authorizeExchange()
//                .pathMatchers(Convert.toStrArray(ignoreUrls)).permitAll()
//                .anyExchange().access(resourceServerManager)
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(accessDeniedHandler()) // ???????????????
//                .authenticationEntryPoint(authenticationEntryPoint()) //???????????????
//                .and().csrf().disable();
//
//        return http.build();
//    }
//
//    /**
//     * ????????????????????????
//     */
//    @Bean
//    ServerAccessDeniedHandler accessDeniedHandler() {
//        return (exchange, denied) -> {
//            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
//                    .flatMap(response -> ResponseUtils.writeErrorInfo(response, ResultCode.ACCESS_UNAUTHORIZED));
//            return mono;
//        };
//    }
//
//    /**
//     * token????????????????????????????????????
//     */
//    @Bean
//    ServerAuthenticationEntryPoint authenticationEntryPoint() {
//        return (exchange, e) -> {
//            Mono<Void> mono = Mono.defer(() -> Mono.just(exchange.getResponse()))
//                    .flatMap(response -> ResponseUtils.writeErrorInfo(response, ResultCode.TOKEN_INVALID_OR_EXPIRED));
//            return mono;
//        };
//    }
//
//    /**
//     * ServerHttpSecurity?????????jwt???authorities?????????????????????Authentication
//     * ?????????jwt???Claim??????authorities??????
//     * ??????????????????????????????????????????????????????JwtGrantedAuthoritiesConverter
//     */
//    @Bean
//    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(SecurityConstants.AUTHORITY_PREFIX);
//        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(SecurityConstants.JWT_AUTHORITIES_KEY);
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
//    }
//
//    /**
//     * ????????????JWT????????????
//     */
//    @SneakyThrows
//    @Bean
//    public RSAPublicKey rsaPublicKey() {
//        Resource resource = new ClassPathResource("public.key");
//        InputStream is = resource.getInputStream();
//        String publicKeyData = IoUtil.read(is).toString();
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec((Base64.decode(publicKeyData)));
//
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
//        return rsaPublicKey;
//    }
//
//}
