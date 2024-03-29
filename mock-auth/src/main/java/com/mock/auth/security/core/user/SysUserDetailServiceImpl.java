package com.mock.auth.security.core.user;

import com.mock.auth.client.UserFeignClient;
import com.mock.auth.dto.AuthUserDTO;
import com.mock.common.result.Result;
import com.mock.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhao
 * @since 2022-06-13 15:51
 */
@Service
@Slf4j
public class SysUserDetailServiceImpl implements UserDetailsService {

//    @Lazy
//    @Autowired
//    private UserServiceImpl userService;

    @Resource
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("--------  执行重写的校验方法");
        SysUserDetails userDetails = null;
        Result<AuthUserDTO> result = userFeignClient.getUserByUsername(username);
        if (Result.isSuccess(result)) {
            AuthUserDTO user = result.getData();
            if (null != user) {
                userDetails = new SysUserDetails(user);
            }
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException(ResultCode.USER_NOT_EXIST.getMsg());
        } else if (!userDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetails;
    }
}
