package com.mock.auth.controller;

import cn.hutool.json.JSONUtil;
import com.mock.common.constant.SecurityConstants;
import com.mock.common.result.Result;
import com.mock.common.util.RequestUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author zhao
 * @since 2022-06-17 16:58
 */
@Api(tags = "认证中心")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private TokenEndpoint tokenEndpoint;

    private KeyPair keyPair;

    @ApiOperation(value = "OAuth2认证", notes = "登录入口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "zhao", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "root", value = "用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "用户密码")
    })
    @PostMapping("/token")
    public Object postAccessToken(
            @ApiIgnore Principal principal,
            @ApiIgnore @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {

        /**
         * 获取登录认证的客户端ID
         *
         * 兼容两种方式获取Oauth2客户端信息（client_id、client_secret）
         * 方式一：client_id、client_secret放在请求路径中(注：当前版本已废弃)
         * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
         */
        String clientId = RequestUtils.getOAuth2ClientId();
        log.info("OAuth认证授权 客户端ID:{}，请求参数：{}", clientId, JSONUtil.toJsonStr(parameters));

        /**
         * knife4j接口文档测试使用
         *
         * 请求头自动填充，token必须原生返回，不能有任何包装，否则显示 undefined undefined
         * 账号/密码:  client_id/client_secret : client/123456
         */
        if (SecurityConstants.TEST_CLIENT_ID.equals(clientId)) {
            return tokenEndpoint.postAccessToken(principal, parameters).getBody();
        }

        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();

        return Result.success(accessToken);
    }



    @ApiOperation(value = "获取公钥")
    @GetMapping("/getPublicKey")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

    @ApiOperation(value = "获取公钥")
    @PostMapping("/test")
    public String test() {
        return "new JWKSet(key).toJSONObject()";
    }

}
