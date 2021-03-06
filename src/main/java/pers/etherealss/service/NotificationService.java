package pers.etherealss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.etherealss.pojo.bo.NotificationBo;
import pers.etherealss.pojo.po.Notification;
import pers.etherealss.pojo.po.Team;
import pers.etherealss.pojo.po.User;

import java.util.List;

/**
 * @author wtk
 * @since 2021-10-31
 */
public interface NotificationService extends IService<Notification> {

    /**
     * 通知队长有人要加入队伍
     * @param sender
     * @param team
     */
    void notifyAddTeanm2Leader(User sender, Team team);

    /**
     * 设为已读
     * @param notificationId
     */
    void hasRead(Long notificationId);


    /**
     * 获取消息
     * @param userId
     * @param dispalyPosition
     * @return
     */
    List<NotificationBo> getNotifications(Integer userId, String dispalyPosition);

    /**
     * 通知队员 已报名比赛
     * @param leaderId
     * @param teamId
     * @param competitionId
     */
    void notifyTeamRegister(Integer leaderId, Integer teamId, Integer competitionId);
}
