package com.mock.auth.client;

import com.mock.auth.client.fallback.UserFeignFallbackClient;
import com.mock.auth.dto.AuthUserDTO;
import com.mock.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "mock-admin", fallback = UserFeignFallbackClient.class)
public interface UserFeignClient {

    @GetMapping("/api/user/username/{username}")
    Result<AuthUserDTO> getUserByUsername(@PathVariable String username);
}
