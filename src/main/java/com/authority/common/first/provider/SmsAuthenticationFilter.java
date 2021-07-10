package com.authority.common.first.provider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 短信登录的鉴权过滤器
 *
 * @ClassName SmsAuthenticationFilter
 * @Author guangmingdexin
 * @Date 2021/7/5 15:58
 * @Version 1.0
 **/
public class SmsAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * form表单中手机号码的字段name
     */
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

    public static final String SPRING_SECURITY_FORM_MOBILE_CODE = "code";

    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;

    private String codeParameter = SPRING_SECURITY_FORM_MOBILE_CODE;

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/sms/login", "POST");

    /**
     * 是否仅 POST 方式
     */
    private boolean postOnly = true;

    public SmsAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    public SmsAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);
        String code = obtainCode(request);

        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();


        SmsAuthenticationToken authRequest = new SmsAuthenticationToken(mobile, code);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);

    }

    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    private String obtainCode(HttpServletRequest request){
        return request.getParameter(codeParameter);
    }

    protected void setDetails(HttpServletRequest request,SmsAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
