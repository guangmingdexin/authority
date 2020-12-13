package com.authority.common.exception;

import com.authority.common.utils.Msg;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
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


    @ExceptionHandler(SQLException.class)
    public HashMap<String, Object> sqlError() {
        System.out.println("sql 操作错误！");
        return Msg.setResult("402", null, "fail");
    }

    @ExceptionHandler(NullPointerException.class)
    public HashMap<String, Object> argNullError() {
        System.out.println("参数不能为空");
        return Msg.setResult("501", null, "fail");
    }
}
