package pers.etherealss.utils.captcha;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.etherealss.utils.captcha.po.LineCaptcha;

import java.io.IOException;

@Slf4j
@DisplayName("CaptchaUtilTest测试")
@SpringBootTest
class CaptchaUtilTest {

    @Test
    void testCreateLineCaptcha() throws IOException {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        //图形验证码写出，可以写出到文件，也可以写出到流
        lineCaptcha.write("d:/line1.png");
        //输出code
        log.debug("{}", lineCaptcha.getCode());
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null) {
            return 0;
        }
        int len = s.length();
        if (len == 0) return 0;
        if (len == 1) return 1;

        int maxLen = -1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            int lastIndex = sb.lastIndexOf(String.valueOf(c));
            if (lastIndex < 0) {
                // 没有重复字符
                sb.append(c);
            } else {
                // 更新最大长度
                maxLen = Math.max(maxLen, sb.toString().length());
                // 更新子串对象
                String substring = sb.substring(lastIndex + 1, sb.length());
                sb = new StringBuilder(substring).append(c);
            }
        }
        // 更新最大长度
        maxLen = Math.max(maxLen, sb.toString().length());
        return maxLen;
    }
}