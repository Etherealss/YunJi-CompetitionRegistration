package pers.etherealss.other;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author wtk
 * @date 2021-10-28
 */
@Slf4j
@DisplayName("JwtTest测试")
@SpringBootTest
public class JwtTest {
    /**
     * 在线解析jwt_token https://jwt.io/
     */
    @Test
    void testCreateJwt() {
        //创建一个JwtBuilder对象
        JwtBuilder jwtBuilder = Jwts.builder()
                // 声明的标识，相当于{"jti":"888"}
                .setId("888")
                // 主体，用户 {"sub":"Rose"}
                .setSubject("Rose")
                // 创建日期 {"ita":"xxxxxx"}
                .setIssuedAt(new Date())
                // 签名手段，参数1：算法，参数2：盐
                .signWith(SignatureAlgorithm.HS256, "xxxx");
        // 获取jwt的token
        String token = jwtBuilder.compact();
        log.debug(token);
        // 三部分的base64解密
        log.debug("--------------------");
        String[] split = token.split("\\.");
        log.debug(Base64Codec.BASE64.decodeToString(split[0]));
        log.debug(Base64Codec.BASE64.decodeToString(split[1]));
        //无法解密
        log.debug(Base64Codec.BASE64.decodeToString(split[2]));
    }

    @Test
    void parseToken() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjM1NDExNTM3fQ.SUMSTKJK_IkfMVt3oKrtOCiHQCevlbTKMbv9mrlVjlo";
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token).getBody();
        log.debug(
                "jti:" + claims.getId() +
                        ",\nsub:" + claims.getSubject() +
                        ",\niat:" + claims.getIssuedAt()
        );
    }

    /**
     * 带过期时间
     */
    @Test
    void testCreateExpireJwt() {
        long now = System.currentTimeMillis();
        // 一分钟后过期
        long exp = now + 60 * 1000;
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("888")
                .setSubject("Rose")
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(exp))
                .signWith(SignatureAlgorithm.HS256, "xxxx");
        // 获取jwt的token
        String token = jwtBuilder.compact();
        log.debug(token);
        String[] split = token.split("\\.");
        log.debug(Base64Codec.BASE64.decodeToString(split[1]));
    }

    @ValueSource(strings = {"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjM1NDEyMzc1LCJleHAiOjE2MzU0MTI0MzV9.lRxK8aeiaYAMlcKby5DJXWBMDfIDvHF_qBjB2tj-TaM"})
    @ParameterizedTest
    void parseExpToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("xxxx")
                .parseClaimsJws(token).getBody();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertThrows(ExpiredJwtException.class, () -> {
            log.debug("签发时间:" + format.format(claims.getIssuedAt()) +
                    ",\n当前时间:" + format.format(new Date()) +
                    ",\n过期时间:" + format.format(claims.getExpiration())
            );
        });

    }
}
