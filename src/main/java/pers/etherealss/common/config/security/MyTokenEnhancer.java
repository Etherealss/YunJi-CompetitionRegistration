package pers.etherealss.common.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import pers.etherealss.pojo.po.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wtk
 * @date 2021-10-28
 */
@Slf4j
@Component
public class MyTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
                                     OAuth2Authentication oAuth2Authentication) {
        User principal = (User)oAuth2Authentication.getPrincipal();
        Map<String, Object> map = new HashMap<>();
        map.put("id", principal.getId());
        map.put("username", principal.getUsername());
        map.put("avatar", principal.getAvatar());
        map.put("registerTime", principal.getRegisterTime());
        map.put("userRole", principal.getUserRole());
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(map);
        return oAuth2AccessToken;
    }
}
