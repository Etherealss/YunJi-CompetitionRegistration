package pers.etherealss.utils.captcha.generator;

import pers.etherealss.utils.simple.RandomUtil;

/**
 * @author wtk
 * @description 抽象验证码生成器
 * 确定了基本的字符集（用于获取随机字符串）以及验证码长度
 * @date 2021-10-05
 */
public abstract class AbstractCaptchaCodeGenerator implements CaptchaCodeGenerator {
    private static final long serialVersionUID = 8685744597154953479L;

    /** 基础字符集合，用于随机获取字符串的字符集合 */
    protected final String baseStr;
    /** 验证码长度 */
    protected final int length;

    /**
     * 构造，使用字母+数字做为基础
     *
     * @param count 生成验证码长度
     */
    public AbstractCaptchaCodeGenerator(int count) {
        this(RandomUtil.BASE_CHAR_NUMBER, count);
    }

    /**
     * 构造
     *
     * @param baseStr 基础字符集合，用于随机获取字符串的字符集合
     * @param length 生成验证码长度
     */
    public AbstractCaptchaCodeGenerator(String baseStr, int length) {
        this.baseStr = baseStr;
        this.length = length;
    }

    /**
     * 获取长度验证码
     *
     * @return 验证码长度
     */
    public int getLength() {
        return this.length;
    }
}

