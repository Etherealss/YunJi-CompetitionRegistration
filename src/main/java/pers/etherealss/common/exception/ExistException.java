package pers.etherealss.common.exception;

import pers.etherealss.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class ExistException extends ServiceException {
    public ExistException() {
        super(ApiInfo.EXIST);
    }

    public ExistException(String message) {
        super(ApiInfo.EXIST, message);
    }
}
