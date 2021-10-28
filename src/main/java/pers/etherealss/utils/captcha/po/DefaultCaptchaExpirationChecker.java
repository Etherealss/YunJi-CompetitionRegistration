package pers.etherealss.utils.captcha.po;

/**
 * @author wtk
 * @description 验证码过期时间检查器
 * @date 2021-10-05
 */
public class DefaultCaptchaExpirationChecker extends CaptchaExpirationChecker {

    /**
     * 超时时间的毫秒值，达到了这个毫秒值就超时
     * @param expirationTime
     */
    public DefaultCaptchaExpirationChecker(long expirationTime) {
        super(expirationTime);
    }

    /**
     * @param timeSpan 几分钟
     */
    public DefaultCaptchaExpirationChecker(int timeSpan) {
        super(timeSpan);
    }

    /**
     * 设置当前系统时间的毫秒值+过期时间长度
     * @param timeSpan 几分钟
     */
    @Override
    public void setTime(int timeSpan) {
        this.setExpirationTime(System.currentTimeMillis() + timeSpan * 60 * 1000);
    }

    /**
     * 计算方式是：判断当前系统时间的毫秒值是否大于失效时间
     */
    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() > this.getExpirationTime();
    }
}
