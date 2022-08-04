package com.mock.admin.netclass.security.userdetails;

import com.mock.admin.netclass.pojo.dto.AuthUserDTO;
import com.mock.admin.netclass.service.impl.UserServiceImpl;
import com.mock.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author zhao
 * @since 2022-06-13 15:51
 */
@Service
@Slf4j
public class SysUserDetailServiceImpl implements UserDetailsService {

    @Lazy
    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("--------  执行重写的校验方法");
        SysUserDetails userDetails = null;
        AuthUserDTO authUserDTO = userService.getAuthInfoByUsername(username);
        if (null != authUserDTO){
            userDetails = new SysUserDetails(authUserDTO);
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
