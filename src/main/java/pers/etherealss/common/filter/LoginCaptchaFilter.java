package pers.etherealss.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.etherealss.common.config.SecurityConfig;
import pers.etherealss.common.enums.AppAttribute;
import pers.etherealss.common.exception.CaptchaException;
import pers.etherealss.utils.captcha.CaptchaUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtk
 * @description 登录验证码检查过滤器
 * @date 2021-10-05
 */
@Slf4j
@Component
public class LoginCaptchaFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 判断是否为登录请求
        boolean isLoginRequest = request.getRequestURI().equals(SecurityConfig.LOGIN_URL) &&
                "POST".equals(StringUtils.upperCase(request.getMethod()));

//        log.debug("【验证码检查过滤器】是否为登录请求：{}", isLoginRequest);

        //如果不是登录请求，直接调用后面的过滤器链
        if (!isLoginRequest) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            this.validate(request);
            filterChain.doFilter(request, response);
        } catch (CaptchaException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
        }

    }

    private void validate(HttpServletRequest request) {
        String userInputCaptcha = request.getParameter(AppAttribute.LOGIN_CAPTCHAC_PARAM);
        CaptchaUtil.checkCaptcha(userInputCaptcha, request);
    }
}
