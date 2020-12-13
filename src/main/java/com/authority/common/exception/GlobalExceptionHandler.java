package com.authority.common.exception;

import com.authority.common.utils.Msg;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理 未生效
 * @Author guangmingdexin
 * @Date 2020/12/13 14:50
 * @Version 1.0
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public HashMap<String, Object> jwtExpired() {
        System.out.println("token 过期了");
        return Msg.setResult("403", null, "token 已过期！");
    }


}
