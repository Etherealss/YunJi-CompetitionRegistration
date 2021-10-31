package pers.etherealss.service;


import com.baomidou.mybatisplus.extension.service.IService;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;

import java.io.BufferedInputStream;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wtk
 * @since 2021-10-03
 */
public interface UserService extends IService<User> {

    Msg<User> login(String username, String password);

    Msg<User> register(User user);

    BufferedInputStream getUserAvatar(String avatar);
}
