package com.mock.auth.security.core.clientdetails;

import com.mock.auth.client.OAuthClientFeignClient;
import com.mock.auth.dto.AuthClientDTO;
import com.mock.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

/**
 * OAuth2 客户端信息
 */
@Service
@RequiredArgsConstructor
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final OAuthClientFeignClient oAuthClientFeignClient;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Cacheable(cacheNames = "auth", key = "'oauth-client:'+#clientId")
    public ClientDetails loadClientByClientId(String clientId) {
        try {
            Result<AuthClientDTO> result = oAuthClientFeignClient.getOAuth2ClientById(clientId);
            if (Result.success().getCode().equals(result.getCode())) {
                AuthClientDTO client = result.getData();
                BaseClientDetails clientDetails = new BaseClientDetails(
                        client.getClientId(),
                        client.getResourceIds(),
                        client.getScope(),
                        client.getAuthorizedGrantTypes(),
                        client.getAuthorities(),
                        client.getWebServerRedirectUri()
                );
//                clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + client.getClientSecret());
                clientDetails.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
                clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
                clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
                return clientDetails;
            } else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (Exception var4) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }
}
