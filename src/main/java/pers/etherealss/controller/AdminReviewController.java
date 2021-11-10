package pers.etherealss.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.etherealss.common.enums.PublishState;
import pers.etherealss.common.exception.MissingParamException;
import pers.etherealss.pojo.po.Competition;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.AdminReviewService;
import pers.etherealss.service.CompetitionService;
import pers.etherealss.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author wtk
 * @date 2021-11-09
 */
@Slf4j
@RestController
@RequestMapping("/admins/review")
public class AdminReviewController {

    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private AdminReviewService adminReviewService;

    @PostMapping("/competitions")
    public Msg<?> reviewCompetition(HttpServletRequest req,@RequestBody Map<String, String> map) {
        User user = TokenUtil.getUserByToken(req);
        String t = map.get("competitionId");
        if (t == null) {
            throw new MissingParamException("审核比赛 competitionId缺失");
        }
        Integer compId = Integer.valueOf(t);
        t = map.get("action");
        if (t == null) {
            throw new MissingParamException("审核比赛 action缺失");
        }
        Boolean action = Boolean.valueOf(t);
        Msg<Competition> msg = competitionService.getOne4State(compId, PublishState.REVIEWING);
        return adminReviewService.reviewCompetition(user, msg.getData(), action);
    }
}
