package com.mock.auth.security.filter;//package com.mock.admin.netclass.security.filter;
//
//import com.mock.admin.netclass.util.JwtTokenUtil;
//import lombok.SneakyThrows;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collections;
//
///**
// *
// * 授权拦截器，如果当前request有token的话
// * 从Header中拿到token，对token进行鉴权
// * 然后把用户放到context中
// *
// * @author zhao
// * @since 2022-06-14 16:58
// * @description: 登录认证过滤器
// */
//public class TokenAuthenticationFilter extends BasicAuthenticationFilter {
//
//    //构造函数
//    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    @SneakyThrows
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain) throws IOException, ServletException {
//
//        logger.info("走了这个方法");
//        String tokenHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
//        // 如果请求头中没有Authorization信息则直接放行了
//        if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        // 如果请求头中有token，则进行解析，并且设置认证信息
//        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
//        super.doFilterInternal(request, response, chain);
//    }
//
//    // 这里从token中获取用户信息并新建一个token 就是上面说的设置认证信息
//    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) throws Exception {
//
//        String token = tokenHeader.replace(JwtTokenUtil.TOKEN_PREFIX, "");
//        // 检测token是否过期 如果过期会自动抛出错误
//        JwtTokenUtil.isExpiration(token);
//        String username = JwtTokenUtil.getUsername(token);
//        String role = JwtTokenUtil.getUserRole(token);
//        if (username != null) {
//            return new UsernamePasswordAuthenticationToken(username, null,
//                    Collections.singleton(new SimpleGrantedAuthority(role))
//            );
//        }
//        return null;
//    }
//
//}
