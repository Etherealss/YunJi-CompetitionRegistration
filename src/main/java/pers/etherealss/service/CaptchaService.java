package pers.etherealss.service;

import pers.etherealss.utils.captcha.po.AbstractCaptcha;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
public interface CaptchaService {

    /**
     * 获取验证码
     * @return
     */
    AbstractCaptcha getCaptcha();

    /**
     * 检查用户输入的验证码
     * @param captcha
     * @param userInput
     * @return
     */
    boolean checkCaptchar(String captcha, String userInput);
}
