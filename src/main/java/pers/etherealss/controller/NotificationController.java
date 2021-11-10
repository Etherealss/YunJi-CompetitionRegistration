package pers.etherealss.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.etherealss.pojo.bo.NotificationBo;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.NotificationService;
import pers.etherealss.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wtk
 * @since 2021-10-31
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * 获取组队通知信息
     * @param request
     * @return
     */
    @GetMapping("/teams")
    public Msg<List<NotificationBo>> getTeamNotifications(HttpServletRequest request) {
        User user = TokenUtil.getUserByToken(request);
        return Msg.ok(notificationService.getTeamNotifications(user.getId()));
    }

    /**
     * 获取赛事通知信息
     * @param request
     * @return
     */
    @GetMapping("/competitions")
    public Msg<List<NotificationBo>> getCompetitionNotifications(HttpServletRequest request) {
        User user = TokenUtil.getUserByToken(request);
        return Msg.ok(notificationService.getCompetitionNotifications(user.getId()));
    }

    /**
     * 获取系统通知信息
     * @param request
     * @return
     */
    @GetMapping("/systems")
    public Msg<List<NotificationBo>> getSystemNotifications(HttpServletRequest request) {
        User user = TokenUtil.getUserByToken(request);
        return Msg.ok(notificationService.getSystemNotifications(user.getId()));
    }

}

