package pers.etherealss.utils.test;

/**
 * @author wtk
 * @description 测试方法接口，可用于lambda
 * @date 2021-10-05
 */
public interface TestBlock<T> {

    /**
     * 测试代码
     * @param i 第几次
     * @return
     */
    T test(int i);
}