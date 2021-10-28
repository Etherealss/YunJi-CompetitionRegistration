package pers.etherealss.manage;

import org.springframework.stereotype.Component;
import pers.etherealss.pojo.po.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wtk
 * @date 2021-10-23
 */
@Component
public class UserCache {

    private final Map<String, User> userCache = new ConcurrentHashMap<>();

    public void addLoggedUser(User user) {
        userCache.put(user.getUsername(), user);
    }

    public User getLoggedUser(String username) {
        return userCache.get(username);
    }

}
