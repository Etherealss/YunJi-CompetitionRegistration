package pers.etherealss.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.etherealss.common.enums.UserRole;
import pers.etherealss.mapper.OfficialMapper;
import pers.etherealss.mapper.StudentMapper;
import pers.etherealss.mapper.UserMapper;
import pers.etherealss.pojo.po.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @description SpringSecurity 用户访问
 * @date 2021-10-03
 */
@Service("userDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private OfficialMapper officialMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("UserDetailsService 用户信息检查：username = " + username);
        // 根据用户名查询数据库
        User user = userMapper.selectUserByUsername(username);
        //判断
        if (user == null) {
            log.info("登录用户不存在,username = {}。", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }

        // 获取权限
        List<String> pers = userMapper.selectUserPermissions(user.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(pers.size());
        for (String authority : pers) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("all"));
        user.setAuthorities(grantedAuthorities);

        switch (user.getUserRole()) {
            case UserRole.STUDENT:
                user.setUserInfo(studentMapper.selectById(user.getId()));
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_student"));
                break;
            case UserRole.OFFICICAL:
                user.setUserInfo(officialMapper.selectById(user.getId()));
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_official"));
                break;
            case UserRole.ADMIN:
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_admin"));
                break;
            default:
        }

        return user;
    }
}
