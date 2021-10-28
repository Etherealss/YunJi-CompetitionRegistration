package pers.etherealss.utils.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.etherealss.utils.test.TestExcutor;

@Slf4j
@DisplayName("RandomUtilTest测试")
@SpringBootTest
class RandomUtilTest {

    @Test
    void testRandomInt() {
        TestExcutor.test(times -> RandomUtil.randomInt(), 10);
    }

    @Test
    void testTestRandomInt() {
        TestExcutor.test(times -> RandomUtil.randomInt(1000), 10);
    }

    @Test
    void testRandomLong() {
        TestExcutor.test(times -> RandomUtil.randomLong(), 10);
    }

    @Test
    void testTestRandomLong() {
        TestExcutor.test(times -> RandomUtil.randomLong(454546464), 10);
    }

    @Test
    void testRandomDouble() {
        TestExcutor.test(times -> RandomUtil.randomDouble(-151515,202020.222D), 10);
    }

    @Test
    void testTestRandomDouble() {
        TestExcutor.test(times -> RandomUtil.randomDouble(), 10);
    }

    @Test
    void testTestRandomDouble1() {
        TestExcutor.test(times -> RandomUtil.randomDouble(10), 10);
    }
}