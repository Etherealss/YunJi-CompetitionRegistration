package pers.etherealss.other;

import org.junit.jupiter.api.Test;
import org.springframework.util.DigestUtils;

/**
 * @author wtk
 * @description
 * @date 2021-10-16
 */
public class MD5Test {
    @Test
    void testMD5() {
        System.out.println(DigestUtils.md5DigestAsHex("123123".getBytes()));
    }
}
