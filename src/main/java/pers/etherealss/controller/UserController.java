package pers.etherealss.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.etherealss.common.enums.ApiInfo;
import pers.etherealss.common.enums.UserRole;
import pers.etherealss.common.exception.MissingParamException;
import pers.etherealss.pojo.po.Official;
import pers.etherealss.pojo.po.Student;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.CaptchaService;
import pers.etherealss.service.OfficialService;
import pers.etherealss.service.StudentService;
import pers.etherealss.service.UserService;
import pers.etherealss.utils.GetParamUtil;
import pers.etherealss.utils.ResponseUtil;
import pers.etherealss.utils.TokenUtil;
import pers.etherealss.utils.captcha.CaptchaUtil;
import pers.etherealss.utils.simple.FileUtil;
import pers.etherealss.utils.simple.ImgUtil;
import pers.etherealss.utils.simple.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.IOException;

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
    private StudentService studentService;
    @Autowired
    private OfficialService officialService;
    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/login")
    public Msg<User> login(HttpServletRequest request) {
        log.info("用户登录，获取信息");
        return Msg.ok(TokenUtil.getUserByToken(request));
    }

    /**
     * 获取当前用户信息
     * @return
     */
    @GetMapping("/curUser")
    public Msg<User> getCurUser(HttpServletRequest request) {
        log.trace("获取当前用户信息");
        User user = TokenUtil.getUserByToken(request);
        user = userService.getById(user.getId());
        if (UserRole.STUDENT.equals(user.getUserRole())) {
            Student student = studentService.getById(user.getId());
            user.setUserInfo(student);
        } else if (UserRole.OFFICICAL.equals(user.getUserRole())) {
            Official official = officialService.getById(user.getId());
            user.setUserInfo(official);
        }
        return Msg.ok(user);
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
    @PostMapping("/public/register")
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

        User register = userService.register(user);

        return Msg.ok(register);
    }

    /**
     * 获取头像。因为数据可能超过get的上限所以需要用post
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @PostMapping("/avatar")
    public Msg<String> getUserAvatarStream(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = TokenUtil.getUserByToken(request);
        BufferedInputStream userAvatar = userService.getUserAvatar(user.getAvatar());
        String data = ImgUtil.getImg4Base64(FileUtil.bytes(userAvatar));
        Msg<String> msg = new Msg<>(ApiInfo.OK);
        msg.setData(data);
        return msg;
    }

    /**
     * 获取其他用户的头像
     * @param response
     * @param avatarPath
     * @return
     * @throws IOException
     */
    @GetMapping("/public/avatar/{avatarPath}")
    public void getUserAvatarStream(HttpServletResponse response, @PathVariable String avatarPath) throws IOException {
        if (StringUtil.isBlank(avatarPath)) {
            throw new MissingParamException("头像路径缺失");
        }
        BufferedInputStream userAvatar = userService.getUserAvatar(avatarPath);
        ResponseUtil.sendFile(response, userAvatar);
//        String data = ImgUtil.getImg4Base64(FileUtil.bytes(userAvatar));
//        Msg<String> msg = new Msg<>(ApiInfo.OK);
//        msg.setData(data);
//        return msg;
    }
}

