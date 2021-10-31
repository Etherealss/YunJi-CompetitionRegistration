package pers.etherealss.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.etherealss.pojo.bo.TeamBo;
import pers.etherealss.pojo.po.Team;
import pers.etherealss.service.TeamService;

import java.util.List;

@Slf4j
@DisplayName("TeamServiceImplTest测试")
@SpringBootTest
class TeamServiceImplTest {

    @Autowired
    private TeamService teamService;

    @Test
    void testGet() {
        Team byId = teamService.getById(1L);
        log.debug("{}", byId);
    }

    @Test
    void testGetTeamBo() {
        TeamBo teamBo = teamService.getTeamBo(1);
        log.debug("{}", teamBo);
    }

    @Test
    void testGetTeamBoByLeader() {
        List<TeamBo> teamBoByLeader = teamService.getTeamBoByLeader(1);
        log.debug("{}", teamBoByLeader);
    }
}