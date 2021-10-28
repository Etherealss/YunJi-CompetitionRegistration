package pers.etherealss.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import pers.etherealss.pojo.po.User;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author wtk
 * @date 2021-10-28
 */
public class TokenUtil {

    public static final byte[] JWT_SIGN_KEY = "yun_ji".getBytes(StandardCharsets.UTF_8);

    /**
     * token 存放的请求头的位置
     */
    public final static String TOKEN_HEADER = "Authorization";
    /**
     * token类型
     */
    public final static String TOKEN_TYPE = "Bearer";
    /**
     * token类型的字符串长度，用于截取字符串
     */
    public final static int TOKEN_TYPE_LENGTH = TOKEN_TYPE.length() + 1;

    /**
     * 从request中获取token
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String authorization = request.getHeader(TOKEN_HEADER);
        String token = authorization.substring(authorization.indexOf(TOKEN_TYPE) + TOKEN_TYPE_LENGTH);
        return token;
    }

    public static Claims getTokenClaims(HttpServletRequest request) {
        return getTokenClaims(getToken(request));
    }

    public static Claims getTokenClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SIGN_KEY).parseClaimsJws(token).getBody();
    }

    public static User getUserByToken(HttpServletRequest request) {
        Claims tokenClaims = getTokenClaims(request);
        User user = new User();
        user.setAvatar(tokenClaims.get("avatar", String.class));
        user.setUsername(tokenClaims.get("username", String.class));
        user.setRegisterTime(tokenClaims.get("registerTime", Date.class));
        return user;
    }
}
