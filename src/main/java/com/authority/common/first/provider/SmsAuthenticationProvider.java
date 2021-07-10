package com.authority.common.first.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName SmsAuthenticationProvider
 * @Author guangmingdexin
 * @Date 2021/7/5 16:15
 * @Version 1.0
 **/
@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserDetailsService smsUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;

        // 校验电话号码
        String mobile = (String) authenticationToken.getPrincipal();
        String code = (String) authenticationToken.getCredentials();

        UserDetails userDetails = smsUserService.loadUserByUsername(mobile);

        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
        if(userDetails.getUsername() != null && userDetails.getPassword().equals(code)) {
            SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
            authenticationResult.setDetails(authenticationToken.getDetails());

            return authenticationResult;
        }else {
            throw new BadCredentialsException("验证码错误");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
