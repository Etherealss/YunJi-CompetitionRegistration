package pers.etherealss.common.exception;

import pers.etherealss.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class MissingParamException extends ServiceException {
    public MissingParamException() {
        super(ApiInfo.MISSING_PARAM);
    }

    public MissingParamException(String message) {
        super(ApiInfo.MISSING_PARAM, message);
    }
}
