package pers.etherealss.utils.captcha.po;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
public interface Captcha extends Serializable {
    /**
     * 创建验证码，实现类需同时生成随机验证码字符串和验证码图片
     */
    void createCode() throws IOException;

    /**
     * 获取验证码的文字内容
     *
     * @return 验证码文字内容
     */
    String getCode();

    /**
     * 验证验证码是否正确，建议忽略大小写
     *
     * @param userInputCode 用户输入的验证码
     * @return 是否与生成的一致
     */
    boolean verify(String userInputCode);

    /**
     * 将验证码写出到目标流中
     * 如果验证码图片还未生成，则会自动生成
     *
     * @param out 目标流
     */
    void write(OutputStream out) throws IOException;

}
