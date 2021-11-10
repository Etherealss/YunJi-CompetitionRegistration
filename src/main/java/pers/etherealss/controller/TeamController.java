package pers.etherealss.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pers.etherealss.pojo.bo.TeamBo;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.NotificationService;
import pers.etherealss.service.TeamService;
import pers.etherealss.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author wtk
 * @since 2021-10-03
 */
@Slf4j
@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private NotificationService notiService;

    @Secured("ROLE_student")
    @GetMapping("/myTeams")
    public Msg<List<TeamBo>> getMyTeam(HttpServletRequest req) {
        User user = TokenUtil.getUserByToken(req);
        return Msg.ok(teamService.getJoinedTeamBo(user.getId()));
    }

    @Secured("ROLE_student")
    @GetMapping("/leadTeams")
    public Msg<List<TeamBo>> getTeamsbyLeader(HttpServletRequest req) {
        User user = TokenUtil.getUserByToken(req);
        return Msg.ok(teamService.getTeamBoByLeader(user.getId()));
    }

    @Secured("ROLE_student")
    @PostMapping("/create")
    public Msg<List<TeamBo>> createTeam(HttpServletRequest req, @RequestBody String teamName) {
        User user = TokenUtil.getUserByToken(req);
        teamService.createTeam(user.getId(), teamName);
        return Msg.ok();
    }

    @Secured("ROLE_student")
    @PostMapping("/addByInviteCode")
    public Msg<Object> requestAddTeamByInviteCode(HttpServletRequest req, @RequestBody String inviteCode) {
        User user = TokenUtil.getUserByToken(req);
        teamService.requestAddTeamByInviteCode(user.getId(), inviteCode);
        return Msg.ok();
    }

    @Secured("ROLE_student")
    @PostMapping("/respAddTeam")
    public Msg<Object> respAddTeam(HttpServletRequest req, @RequestBody Map<String, String> map) {
        Long notificationId = Long.valueOf(map.get("notificationId"));
        Boolean action = Boolean.valueOf(map.get("action"));
        log.debug("notificationId = {}, action = {}", notificationId, action);
        User leader = TokenUtil.getUserByToken(req);
        // 响应 加入队伍 请求
        teamService.respAddTeam(leader, notificationId, action);
        return Msg.ok();
    }
}

