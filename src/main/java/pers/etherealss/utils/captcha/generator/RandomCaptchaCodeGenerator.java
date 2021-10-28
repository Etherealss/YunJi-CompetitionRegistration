package pers.etherealss.utils.captcha.generator;

import pers.etherealss.utils.simple.RandomUtil;
import pers.etherealss.utils.simple.StringUtil;

/**
 * @author wtk
 * @description 随机字符验证码生成器
 * @date 2021-10-05
 */
public class RandomCaptchaCodeGenerator extends AbstractCaptchaCodeGenerator {

    public RandomCaptchaCodeGenerator(int count) {
        super(count);
    }

    /**
     * @param baseStr 基础字符集合，用于随机获取字符串的字符集合
     * @param length 生成验证码长度
     */
    public RandomCaptchaCodeGenerator(String baseStr, int length) {
        super(baseStr, length);
    }

    @Override
    public String generate() {
        // 获取随机字符串序列
        return RandomUtil.randomString(this.baseStr, this.length);
    }

    @Override
    public boolean verify(String code, String userInputCode) {
        if (StringUtil.isNotBlank(userInputCode)) {
            return StringUtil.equalsIgnoreCase(code, userInputCode);
        }
        return false;
    }
}
