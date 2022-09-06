package com.mock.auth.security.core.user;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.mock.auth.dto.AuthUserDTO;
import com.mock.common.constant.GlobalConstants;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zhao
 * @since 2022-06-13 10:17
 */
@Data
public class SysUserDetails implements UserDetails {

    /** 主键id */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickName;

    /** 用户是否可用 */
    private boolean enabled;

    private Collection<SimpleGrantedAuthority> authorities;

    public SysUserDetails(String username, String password) {
        this.setUserName(username);
        this.setPassword(password);
    }

    /**
     * 系统管理用户
     * @return
     */
    public SysUserDetails(AuthUserDTO userDTO) {
        this.setUserId(userDTO.getUserId());
        this.setUserName(userDTO.getUsername());
        this.setPassword(userDTO.getPassword());
        this.setEnabled(GlobalConstants.STATUS_YES.equals(userDTO.getStatus()));
        if (CollectionUtils.isNotEmpty(userDTO.getRoles())){
            authorities = new ArrayList<>();
            userDTO.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
