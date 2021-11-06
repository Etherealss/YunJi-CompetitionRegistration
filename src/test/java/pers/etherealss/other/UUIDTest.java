package pers.etherealss.other;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author wtk
 * @date 2021-11-01
 */
public class UUIDTest {

    @Test
    void test() {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }
}
