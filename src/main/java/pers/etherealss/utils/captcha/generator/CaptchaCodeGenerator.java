package pers.etherealss.utils.captcha.generator;

import java.io.Serializable;

/**
 * @author wtk
 * @description 验证码生成器
 * @date 2021-10-05
 */
public interface CaptchaCodeGenerator extends Serializable {

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    String generate();

    /**
     * 验证用户输入的字符串是否与生成的验证码匹配
     *
     * @param code 生成的随机验证码
     * @param userInputCode 用户输入的验证码
     * @return 是否验证通过
     */
    boolean verify(String code, String userInputCode);
}
