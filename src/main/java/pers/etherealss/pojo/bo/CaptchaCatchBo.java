package pers.etherealss.pojo.bo;

import lombok.Data;
import pers.etherealss.utils.captcha.generator.CaptchaCodeGenerator;
import pers.etherealss.utils.captcha.po.AbstractCaptcha;
import pers.etherealss.utils.captcha.po.CaptchaExpirationChecker;

/**
 * @author wtk
 * @description 用来放在Session中的验证码缓存对象
 * @date 2021-10-05
 */
@Data
public class CaptchaCatchBo {

    /** 验证码，用于输出与验证码图片，也会用于检验 */
    private String code;
    /** 验证码生成器，可以验证 */
    private CaptchaCodeGenerator generator;
    /** 过期检查器 */
    private CaptchaExpirationChecker expirationHandler;



    public CaptchaCatchBo(AbstractCaptcha captcha) {
        this.code = captcha.getCode();
        this.generator = captcha.getGenerator();
        this.expirationHandler = captcha.getExpirationHandler();
    }

    public CaptchaCatchBo(String code, CaptchaCodeGenerator generator, CaptchaExpirationChecker expirationHandler) {
        this.code = code;
        this.generator = generator;
        this.expirationHandler = expirationHandler;
    }

    public CaptchaCatchBo() {
    }

    public boolean versify(String userInput) {
        return this.getGenerator().verify(this.code, userInput);
    }
}
