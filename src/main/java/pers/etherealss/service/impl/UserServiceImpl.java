package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pers.etherealss.common.exception.ExistException;
import pers.etherealss.common.exception.MismatchException;
import pers.etherealss.common.exception.NotFoundException;
import pers.etherealss.common.exception.ServiceException;
import pers.etherealss.common.properties.ResourcePathProperties;
import pers.etherealss.mapper.UserMapper;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.UserService;
import pers.etherealss.utils.simple.FileUtil;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author wtk
 * @since 2021-10-03
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResourcePathProperties resourcePathProperties;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Msg<User> login(String username, String password) {
        System.out.println("用户登录" + username);
        log.debug("用户登录：{}", username);
        User user = this.getExistUser(username);
        String pwMd5 = DigestUtils.md5DigestAsHex((username + password).getBytes());
        log.debug("MD5密文：{}，长度：{}", pwMd5, pwMd5.length());
        if (!user.getPassword().equals(pwMd5)) {
            throw new MismatchException("密码错误");
        }
        return Msg.ok(user);
    }

    @Override
    public User register(User user) {
        User existUser = userMapper.selectUserByUsername(user.getUsername());
        if (existUser != null) {
            throw new ExistException("用户名已存在");
        }
        // 计算密文
        String encode = passwordEncoder.encode(user.getPassword());
        log.debug("密码密文：{}", encode);
        user.setPassword(encode);
        // 头像
        if (Strings.isBlank(user.getAvatar())) {
            user.setAvatar("/default-boy.png");
        }

        int insert = userMapper.insert(user);

        return user;
    }

    @Override
    public BufferedInputStream getUserAvatar(String avatar) {
        try {
            BufferedInputStream inputStream = FileUtil.getInputStream(
                    resourcePathProperties.getAvatar() + avatar);
            return inputStream;
        } catch (IOException e) {
            log.debug("avatar: {}", avatar);
            throw new ServiceException("IO异常");
        }
    }

    private User getExistUser(String username) {
        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            throw new NotFoundException("用户名不存在");
        }
        return user;
    }
}
