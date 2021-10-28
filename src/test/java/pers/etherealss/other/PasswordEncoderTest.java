package pers.etherealss.other;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author wtk
 * @date 2021-10-25
 */
@Slf4j
@DisplayName("SlideshowMapperTest测试")
@SpringBootTest
public class PasswordEncoderTest {

    @Test
    void testEncode() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123123");
        log.debug("{}", encode);
        log.debug("{}", encoder.matches("123123", encode));
        log.debug("{}", encode.length());
        encode = encoder.encode("jasdjiqld123");
        log.debug("{}", encode);
        log.debug("{}", encode.length());
        encode = encoder.encode("1");
        log.debug("{}", encode);
        log.debug("{}", encode.length());
    }
}
