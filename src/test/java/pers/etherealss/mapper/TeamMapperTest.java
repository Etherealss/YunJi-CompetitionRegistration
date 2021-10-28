package pers.etherealss.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.etherealss.pojo.po.Team;

@Slf4j
@DisplayName("TeamMapperTest测试")
@SpringBootTest
class TeamMapperTest {
    @Autowired
    private TeamMapper teamMapper;

    @Test
    void testGet() {
        Team team = teamMapper.selectById(1);
        log.debug("{}", team);
    }
}