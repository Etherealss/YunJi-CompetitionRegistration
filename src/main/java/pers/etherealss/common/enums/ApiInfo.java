package pers.etherealss.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum ApiInfo {

    OK(200, "OK"),

    BAD_REQUEST(400, "请求报文语法错误[参数校验失败]"),
    UNAUTHORIZED(401, "[未认证身份]"),
    FORBIDDEN_REQUEST(403, "[没有权限]"),
    NOT_FOUND(404, "[资源不存在]"),

    SERVER_ERROR(500, "[服务运行异常]"),
    SERVICE_UN_AVAILABLE(503, "[服务不可用]"),
    SERVER_BUSY(505, "[服务繁忙]"),

    MISSING_PARAM(4001, "[参数缺失]"),
    ERROR_PARAM(4002, "[参数不合法]"),
    EXIST(4003, "[目标已存在]"),
    MISMATCH(4004, "[信息不匹配]"),
    REQUEST_UNSUPPORTED(4005, "[请求不支持]"),

    AUTHORIZATION_FAILED(4011, "[认证未通过]"),

    PASSWORD_ERROR(10201, "[密码错误]"),
    LOGIN_USER_NOT_FOUND(10202, "[登录用户不存在]"),
    USER_LOGGED(10203, "[用户已登录]"),

    CAPTCHA_MISSING(10301, "[未输入验证码]"),
    CAPTCHA_NOT_MATCH(10302, "[验证码不匹配]"),
    CAPTCHA_INVALID(10303, "[验证码已失效]"),
    CAPTCHA_ERROR(10304, "[验证码异常]"),

    TOKEN_MISSING(10401, "[token缺失]"),
    TOKEN_EXP(10402, "[token缺失]"),

    TEAM_HAS_JIONED(10501, "[用户已经在队伍中]"),

    TEAM_HAS_REGISTER(10601, "[队伍已报名]"),

    HAS_PUBLISHED(10701, "[已发布]"),
    /** 审核中 */
    IS_REVIEWING(10702, "[正在审核]"),
    /** 草稿 */
    IS_DRAFT(10703, "[未发布]"),

    ORGANIZATION_HAS_JIONED(10801, "[用户已经在组织中]"),

    ;

    int code;
    String message;


    @Override
    public String toString() {
        return super.toString();
    }
}
