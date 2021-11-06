package pers.etherealss.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.NotificationService;
import pers.etherealss.service.RegistrationService;
import pers.etherealss.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wtk
 * @since 2021-10-03
 */
@Slf4j
@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService regService;
    @Autowired
    private NotificationService notiService;

    @PostMapping("/register")
    public Msg<Object> registerCompetition(HttpServletRequest req, @RequestBody Map<String, String> map) {
        User user = TokenUtil.getUserByToken(req);
        Integer teamId = Integer.valueOf(map.get("teamId"));
        Integer competitionId = Integer.valueOf(map.get("competitionId"));
        log.info("competitionId = {}, teamId = {}", competitionId, teamId);
        regService.registerCompetition(teamId, competitionId);
        notiService.notifyTeamRegister(user.getId(), teamId, competitionId);
        return Msg.ok();
    }
}

