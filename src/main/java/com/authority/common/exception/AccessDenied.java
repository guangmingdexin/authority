package com.authority.common.exception;

import com.authority.common.utils.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AccessDeniedHandler
 * @Description TODO
 * @Author guangmingdexin
 * @Date 2020/12/13 20:26
 * @Version 1.0
 **/
@Component
public class AccessDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.getWriter().write(new ObjectMapper().writeValueAsString(Msg.setResult("403", null, "权限不足！")));
    }
}
