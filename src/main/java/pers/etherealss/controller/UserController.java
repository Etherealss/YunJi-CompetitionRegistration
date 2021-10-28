package pers.etherealss.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.etherealss.common.exception.MissingParamException;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.CaptchaService;
import pers.etherealss.service.UserService;
import pers.etherealss.utils.GetParamUtil;
import pers.etherealss.utils.TokenUtil;
import pers.etherealss.utils.captcha.CaptchaUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 * @author wtk
 * @since 2021-10-03
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CaptchaService captchaService;

//    @PostMapping("/login")
//    public Msg<User> login(String username, String password) {
//        return userService.login(username, password);
//    }

    /**
     * 获取当前用户信息
     * @return
     */
    @GetMapping("/curUser")
    public Msg<User> getCurUser(HttpServletRequest request) {
        log.trace("获取当前用户信息");
        return Msg.ok(TokenUtil.getUserByToken(request));
    }

    @GetMapping("/username_exist/{username}")
    public Msg<Boolean> usernameExist(@PathVariable String username) {
        log.trace("查看用户名是否已存在：{}", username);
        int count = userService.count(new QueryWrapper<User>().eq("username", username));
        return Msg.ok(count == 1);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Msg<User> register(HttpServletRequest request, HttpSession httpSession) {
        JSONObject jsonByJson = GetParamUtil.getJsonByJson(request);
        if (jsonByJson == null || jsonByJson.getInnerMap().size() == 0) {
            throw new MissingParamException();
        }
        JSONObject userParam = (JSONObject) jsonByJson.getInnerMap().get("user");
        User user = GetParamUtil.getObjByParam(userParam, User.class);
        log.trace("用户注册：{}", user);

        // 检查验证码
        String userInputCaptcha = jsonByJson.getString("captcha");
        CaptchaUtil.checkCaptcha(userInputCaptcha, request);

        Msg<User> register = userService.register(user);

        return register;
    }
}

