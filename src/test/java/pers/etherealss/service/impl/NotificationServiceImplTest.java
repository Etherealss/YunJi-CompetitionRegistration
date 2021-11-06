package pers.etherealss.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pers.etherealss.pojo.po.Team;
import pers.etherealss.pojo.po.User;
import pers.etherealss.service.NotificationService;
import pers.etherealss.service.TeamService;
import pers.etherealss.service.UserService;

@Slf4j
@DisplayName("NotificationServiceImplTest测试")
@SpringBootTest
class NotificationServiceImplTest {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamService teamService;
    @Transactional
    @Test
    void testNotifyAddTean4Leader() {
        User user = userService.getById(2);
        Team team = teamService.getById(1);
        notificationService.notifyAddTeanm2Leader(user, team);
    }
}