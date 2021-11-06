package pers.etherealss.common.exception;

import pers.etherealss.common.enums.ApiInfo;

/**
 * @author wtk
 * @description 自定义 普通异常（HttpStatus返回200）
 * @date 2021-08-12
 */
public class SimpleException extends ServiceException {
    public SimpleException(ApiInfo apiInfo) {
        super(apiInfo);
    }
}
