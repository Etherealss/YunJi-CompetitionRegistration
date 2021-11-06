package pers.etherealss.other;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import pers.etherealss.common.enums.RedisKey;
import pers.etherealss.mapper.StudentMapper;
import pers.etherealss.mapper.UserMapper;
import pers.etherealss.pojo.po.User;

@Slf4j
@DisplayName("TeamServiceImplTest测试")
@SpringBootTest
class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;


    @Test
    void testRedisHash() {
        int id = 1;
        User user = (User) redisTemplate.opsForHash().get(RedisKey.USER_KEY, id);
        if (user == null) {
            user = userMapper.selectById(id);
            user.setUserInfo(studentMapper.selectStudentProfileById(id));
            redisTemplate.opsForHash().put(RedisKey.USER_KEY, id, user);
        }
        user = (User) redisTemplate.opsForHash().get(RedisKey.USER_KEY, id);
        log.debug("{}", user);

    }
}