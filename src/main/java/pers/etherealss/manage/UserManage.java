package pers.etherealss.manage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import pers.etherealss.common.enums.RedisKey;
import pers.etherealss.common.enums.UserRole;
import pers.etherealss.common.exception.ServiceException;
import pers.etherealss.mapper.OfficialMapper;
import pers.etherealss.mapper.StudentMapper;
import pers.etherealss.mapper.UserMapper;
import pers.etherealss.pojo.po.User;

/**
 * @author wtk
 * @date 2021-10-23
 */
@Slf4j
@Component
public class UserManage {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private OfficialMapper officialMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存查用户+学生
     * @param userId
     * @return
     */
    public User getUserProfile(Integer userId) {
        return getUserProfile(userId, true);
    }

    /**
     * 缓存查用户+学生
     * @param userId
     * @param saveCache
     * @return
     */
    public User getUserProfile(Integer userId, boolean saveCache) {
        // 测试见RedisTest
        User user = (User) redisTemplate.opsForHash().get(RedisKey.USER_KEY, userId);
        if (user == null) {
            user = userMapper.selectById(userId);
            if (user == null) {
                throw new ServiceException("想要查询的用户id不存在：" + userId);
            }
            user.setPassword(null);
            if (UserRole.STUDENT.equals(user.getUserRole())) {
                user.setUserInfo(studentMapper.selectStudentProfileById(userId));
            } else if(UserRole.OFFICICAL.equals(user.getUserRole())) {
                user.setUserInfo(officialMapper.selectOfficialProfileById(userId));
            }
            if (saveCache) {
                redisTemplate.opsForHash().put(RedisKey.USER_KEY, userId, user);
            }
        }
        return user;
    }


}
