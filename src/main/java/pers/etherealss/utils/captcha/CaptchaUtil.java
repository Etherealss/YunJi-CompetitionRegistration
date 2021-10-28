package pers.etherealss.utils.captcha;

import lombok.extern.slf4j.Slf4j;
import pers.etherealss.common.enums.ApiInfo;
import pers.etherealss.common.enums.AppAttribute;
import pers.etherealss.common.exception.CaptchaException;
import pers.etherealss.pojo.bo.CaptchaCatchBo;
import pers.etherealss.utils.captcha.po.LineCaptcha;
import pers.etherealss.utils.simple.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
@Slf4j
public class CaptchaUtil {
    /**
     * 创建线干扰的验证码，默认5位验证码，150条干扰线
     * @param width 图片宽
     * @param height 图片高
     * @return {@link LineCaptcha}
     */
    public static LineCaptcha createLineCaptcha(int width, int height) {
        return new LineCaptcha(width, height);
    }

    /**
     * 检查验证码
     * @param userInputCaptcha
     * @param request
     */
    public static void checkCaptcha(String userInputCaptcha,
                                    HttpServletRequest request) {
        HttpSession session = request.getSession();
        CaptchaCatchBo catchBo = (CaptchaCatchBo) session.getAttribute(AppAttribute.CAPTCHAC_CACHE);
        log.debug("用户输入验证码：{}，验证码对象：{}", userInputCaptcha, catchBo);

        // TODO 用来测试的，这样子就不用每次都写验证码。之后记得删掉
        if ("1234".equals(userInputCaptcha)) {
            return;
        }
        if (StringUtil.isBlank(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_MISSING, "用户输入的验证码的值不能为空！");
        }
        if (catchBo == null) {
            throw new CaptchaException(ApiInfo.CAPTCHA_ERROR, "验证码对象不存在！");
        }
        if (catchBo.getExpirationHandler().isExpired()) {
            throw new CaptchaException(ApiInfo.CAPTCHA_INVALID, "验证码已过期！");
        }
        if (!catchBo.versify(userInputCaptcha)) {
            throw new CaptchaException(ApiInfo.CAPTCHA_NOT_MATCH, "验证码不正确！用户输入："
                    + userInputCaptcha + "；验证码：" + catchBo.getCode());
        }
        removeCaptcha(session);
    }

    private static void removeCaptcha(HttpSession session) {
        session.removeAttribute(AppAttribute.CAPTCHAC_CACHE);
    }

}
