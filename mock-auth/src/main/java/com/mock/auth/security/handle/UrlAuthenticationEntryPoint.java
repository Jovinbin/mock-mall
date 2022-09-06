package com.mock.auth.security.handle;//package com.mock.admin.netclass.security.handle;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mock.common.result.Result;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * @author zhao
// * @since 2022-06-15 9:41
// */
//@Component
//public class UrlAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        Result resultInfo = new Result();
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter writer = response.getWriter();
//        resultInfo.setData(false);
//        resultInfo.setMsg("未登录，请先登录");
//        ObjectMapper mapper = new ObjectMapper();
//        String resultJson = mapper.writeValueAsString(resultInfo);
//        writer.write(resultJson);
//        writer.flush();
//        writer.close();
//    }
//}