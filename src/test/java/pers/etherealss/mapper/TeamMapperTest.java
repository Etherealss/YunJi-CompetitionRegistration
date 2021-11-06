package pers.etherealss.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pers.etherealss.pojo.po.Team;

import java.util.List;

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

    @Test
    void testselectTeamsByMemberId() {
        List<Team> teams = teamMapper.selectTeamsByMemberId(2);
        teams.forEach((team) -> {
            log.debug("{}", team);
        });
    }

    @Transactional
    @Test
    void testinsertNewMember() {
        teamMapper.insertNewMember(2,3);
    }

    @Test
    void testselectExistMemberInTeam() {
        Long aLong = teamMapper.selectExistMemberInTeam(1, 10);
        log.debug("{}", aLong);
    }
}