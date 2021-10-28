package pers.etherealss.utils.captcha.po;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
public abstract class CaptchaExpirationChecker {

    /** 过期时间 */
    protected long expirationTime;

    /**
     * 设置过期时间
     * @param timeSpan
     */
    public abstract void setTime(int timeSpan);

    /**
     * 检查是否过期
     * @return
     */
    public abstract boolean isExpired();

    /**
     * 修改过期时间
     * @param updateTime
     */
    public void updateTime(long updateTime) {
        expirationTime = updateTime;
    }

    public CaptchaExpirationChecker(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public CaptchaExpirationChecker(int timeSpan) {
        setTime(timeSpan);
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
