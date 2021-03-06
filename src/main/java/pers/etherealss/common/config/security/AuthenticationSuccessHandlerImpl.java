package pers.etherealss.common.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtk
 * @description 登录成功处理器
 * @date 2021-10-05
 */
@Slf4j
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess (
            HttpServletRequest request,HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.debug("登录成功");
        User user = (User) authentication.getPrincipal();
        Msg<User> msg = Msg.ok("登录成功");
        msg.setData(user);
        ResponseUtil.send(response, msg);
    }
}
