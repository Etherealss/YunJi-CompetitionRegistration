package pers.etherealss.common.exception;

import pers.etherealss.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class UnsupportedOperationException extends ServiceException {
    public UnsupportedOperationException() {
        super(ApiInfo.REQUEST_UNSUPPORTED);
    }

    public UnsupportedOperationException(String message) {
        super(ApiInfo.REQUEST_UNSUPPORTED, message);
    }
}
