package pers.etherealss.utils.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wtk
 * @description 测试用例驱动器
 * @date 2021-10-05
 */
@Slf4j
public class TestExcutor {

    /**
     * 调用测试代码
     * @param times
     */
    public static <T> void test(TestBlock<T> testBlock, int times) {
        for (int i = 0; i < times; i++) {
            log.debug("测试：{}", testBlock.test(i));
        }
    }

    public static <T> void test(TestBlock<T> testBlock) {
        test(testBlock, 10);
    }
}
