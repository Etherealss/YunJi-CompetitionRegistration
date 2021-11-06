package pers.etherealss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.etherealss.pojo.bo.TeamBo;
import pers.etherealss.pojo.po.Team;
import pers.etherealss.pojo.po.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
public interface TeamService extends IService<Team> {

    /**
     * 获取队伍信息
     * @param id
     * @return
     */
    TeamBo getTeamBo(int id);

    /**
     * 获取用户创建的队伍
     * @param leaderId
     * @return
     */
    List<TeamBo> getTeamBoByLeader(int leaderId);

    /**
     * 获取用户参与的队伍
     * @param userId
     * @return
     */
    List<TeamBo> getJoinedTeamBo(int userId);

    /**
     * 通过邀请码获取
     * @param inviteCode
     * @return
     */
    Team getByInviteCode(String inviteCode);

    /**
     * 创建队伍
     * @param userId
     * @param teamName
     */
    void createTeam(int userId, String teamName);

    /**
     * 请求加入队伍（通过邀请码）
     * @param requestId
     * @param inviteCode
     */
    void requestAddTeamByInviteCode(Integer requestId, String inviteCode);

    /**
     * 响应是否同意加入队伍
     * @param leader
     * @param notificationId
     * @param isAgree
     */
    void respAddTeam(User leader, Long notificationId, Boolean isAgree);
}
