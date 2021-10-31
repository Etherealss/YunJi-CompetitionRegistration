package pers.etherealss.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.etherealss.pojo.bo.TeamBo;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.TeamService;
import pers.etherealss.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wtk
 * @since 2021-10-03
 */
@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/myTeams")
    public Msg<List<TeamBo>> getMyTeam(HttpServletRequest req) {
        User user = TokenUtil.getUserByToken(req);
        return Msg.ok(teamService.getTeamBoByLeader(user.getId()));
    }
}

