package pers.etherealss.common.exception;

import org.springframework.security.core.AuthenticationException;
import pers.etherealss.common.enums.ApiInfo;

/**
 * @author wtk
 * @description 验证码异常
 * @date 2021-10-05
 */
public class CaptchaException extends AuthenticationException {

    private int code;
    private String msg;

    public CaptchaException(ApiInfo apiInfo, String message) {
        super(message);
        this.code = apiInfo.getCode();
        this.msg = apiInfo.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
