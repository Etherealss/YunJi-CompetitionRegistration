package pers.etherealss.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pers.etherealss.common.enums.AppAttribute;
import pers.etherealss.pojo.bo.CaptchaCatchBo;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.CaptchaService;
import pers.etherealss.utils.captcha.po.AbstractCaptcha;
import pers.etherealss.utils.simple.ImgUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
@Controller
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("captcha")
    public void captcha(HttpServletResponse response, HttpSession session) throws IOException {
        AbstractCaptcha captcha = captchaService.getCaptcha();
        captchaCache(captcha, session);

        // 关闭浏览器的缓存
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        captcha.write(response.getOutputStream());
    }

    @GetMapping("captcha_base64")
    public Msg<String> captcha4Base64(HttpServletResponse response, HttpSession session) throws IOException {
        AbstractCaptcha captcha = captchaService.getCaptcha();
        captchaCache(captcha, session);
        byte[] imageBytes = captcha.getImageBytes();
        String img4Base64 = ImgUtil.getImg4Base64(imageBytes);
        return Msg.ok(img4Base64);
    }

    /**
     * 缓存到Session中
     * @param loginCaptcha
     * @param session
     */
    private void captchaCache(AbstractCaptcha loginCaptcha, HttpSession session) {
        CaptchaCatchBo captchaCatchBo = new CaptchaCatchBo(loginCaptcha);
        // 节省空间，只存验证码字符
        session.setAttribute(AppAttribute.CAPTCHAC_CACHE, captchaCatchBo);
    }

}
