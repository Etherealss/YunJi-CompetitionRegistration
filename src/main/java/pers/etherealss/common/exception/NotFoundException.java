package pers.etherealss.common.exception;

import pers.etherealss.common.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class NotFoundException extends ServiceException {
    public NotFoundException() {
        super(ApiInfo.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(ApiInfo.NOT_FOUND, message);
    }
}
