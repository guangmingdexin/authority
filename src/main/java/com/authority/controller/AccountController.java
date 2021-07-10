package com.authority.controller;

import com.authority.common.utils.VerifyCode;
import com.authority.common.utils.token.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AccountController
 * @Author guangmingdexin
 * @Date 2021/7/5 20:45
 * @Version 1.0
 **/
@RestController
@CrossOrigin
public class AccountController {


    @GetMapping(path = "/vercode", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE} )
    public void code(HttpServletRequest req, HttpServletResponse resp) throws IOException, IOException {
        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        String text = vc.getText();
        HttpSession session = req.getSession();
        session.setAttribute("index_code", text);
        session.setMaxInactiveInterval(300);
        VerifyCode.output(image, resp.getOutputStream());

    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


}
