package com.mock.admin.netclass.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mock.admin.netclass.entity.User;
import com.mock.admin.netclass.security.userdetails.SysUserDetails;
import com.mock.admin.netclass.util.JwtTokenUtil;
import com.mock.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication：接收并解析用户凭证。
 * successfulAuthentication：用户成功登录后，这个方法会被调用，我们在这个方法里生成token并返回。
 *
 * @author zhao
 * @since 2022-06-14 17:11
 * @description: 此类使用oauth2后未使用 oauth2的实现方式仍是UsernamePasswordAuthenticationFilter
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    /**
     * 拦截登录请求
     * @param authenticationManager
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("-------  读取到输入流中的登录信息  -------");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User loginUser = new User();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        log.info("读取到输入流中的登录信息{}", loginUser);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
        );
    }

    /**
     * 成功验证后调用的方法
     * 如果成功  就生成token返回
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("-------  登录验证成功  -------");
        SysUserDetails jwtUser = (SysUserDetails) authResult.getPrincipal();

        String role = "";
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        List<String> roles = authorities.stream().map(e -> e.getAuthority()).collect(Collectors.toList());
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        //生成token
        String token = JwtTokenUtil.createToken(jwtUser.getUsername(), roles.toString());
        log.info("生成的token是：{}", token);
        // 返回创建成功的token  但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String tokenStr = JwtTokenUtil.TOKEN_PREFIX + token;
        response.setHeader("token", tokenStr);

        Result resultInfo = new Result();
        response.setStatus(200);
        PrintWriter writer = response.getWriter();
        resultInfo.setData(true);
        resultInfo.setMsg("登录成功");
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(resultInfo);
        writer.write(resultJson);
        writer.flush();
        writer.close();
    }

    /**
     * 失败则返回错误
     *
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("-------  登陆失败  --------");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(401);
        PrintWriter writer = response.getWriter();
        Result resultInfo = new Result();
        resultInfo.setData(false);
        resultInfo.setMsg("用户名或密码不正确，请重新登录");
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = mapper.writeValueAsString(resultInfo);
        writer.write(resultJson);
        writer.flush();
        writer.close();
    }


}
