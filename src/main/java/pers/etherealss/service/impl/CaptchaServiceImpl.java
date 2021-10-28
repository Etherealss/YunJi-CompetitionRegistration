package pers.etherealss.service.impl;

import org.springframework.stereotype.Service;
import pers.etherealss.service.CaptchaService;
import pers.etherealss.utils.captcha.CaptchaUtil;
import pers.etherealss.utils.captcha.po.AbstractCaptcha;
import pers.etherealss.utils.captcha.po.LineCaptcha;
import pers.etherealss.utils.simple.StringUtil;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    public static final int CAPTCHA_LOGIN_WIDTH = 66;
    public static final int CAPTCHA_LOGIN_HEIGHT = 33;
    public static final int CAPTCHA_REGISTER_WIDTH = 66;
    public static final int CAPTCHA_REGISTER_HEIGHT = 33;

    @Override
    public AbstractCaptcha getCaptcha() {
        LineCaptcha lineCaptcha =
                CaptchaUtil.createLineCaptcha(CAPTCHA_LOGIN_WIDTH, CAPTCHA_LOGIN_HEIGHT);
        return lineCaptcha;
    }

    @Override
    public boolean checkCaptchar(String captcha, String userInputCode) {
        if (StringUtil.isNotBlank(userInputCode)) {
            return StringUtil.equalsIgnoreCase(captcha, userInputCode);
        }
        return false;
    }
}
