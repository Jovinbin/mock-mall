package com.mock.admin.netclass.feign;

import com.mock.admin.netclass.pojo.dto.AuthUserDTO;
import com.mock.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zhao
 * @since 2022-06-14 10:53
 */
@FeignClient(value = "mock-admin")
public interface UserFeignClient {

    @GetMapping("/api/user/username/{username}")
    Result<AuthUserDTO> getUserByUsername(@PathVariable String username);

}
