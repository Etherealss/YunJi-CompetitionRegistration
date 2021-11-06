package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.etherealss.common.enums.*;
import pers.etherealss.common.exception.NotFoundException;
import pers.etherealss.common.exception.SimpleException;
import pers.etherealss.manage.NotificationElementSaver;
import pers.etherealss.mapper.*;
import pers.etherealss.pojo.bo.TeamBo;
import pers.etherealss.pojo.po.*;
import pers.etherealss.service.TeamService;
import pers.etherealss.utils.UUIDUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author etherealss
 * @since 2021-10-03
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private NotificationMapper notiMapper;
    @Autowired
    private NotificationElementMapper elementMapper;
    @Autowired
    private NotificationElementSaver elementSaver;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Object> redis;

    @Override
    public TeamBo getTeamBo(int teamId) {
        return wrapTeam(teamId);
    }

    @Override
    public List<TeamBo> getTeamBoByLeader(int leaderId) {
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        wrapper.eq("leader_id", leaderId);
        List<Team> teams = teamMapper.selectList(wrapper);
        List<TeamBo> res = new ArrayList<>(teams.size());
        return wrapTeamList(teams);
    }

    @Override
    public List<TeamBo> getJoinedTeamBo(int userId) {
        // 获取用户已加入的队伍，包括自己身为队长和队员两种情况
        // 队长
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        wrapper.eq("leader_id", userId);
        List<Team> teams = teamMapper.selectList(wrapper);
        // 队员
        teams.addAll(teamMapper.selectTeamsByMemberId(userId));

        List<TeamBo> res = new ArrayList<>(teams.size());
        return wrapTeamList(teams);
    }

    @Override
    public Team getByInviteCode(String inviteCode) {
        return teamMapper.selectOne(
                new QueryWrapper<Team>().eq("invite_code", inviteCode)
        );
    }

    @Override
    public void createTeam(int userId, String teamName) {
        Team team = new Team();
        team.setLeaderId(userId);
        team.setName(teamName);
        team.setInviteCode(UUIDUtil.getUUID());
        log.debug("userId = {}, teamName = {}", userId, teamName);
        teamMapper.insert(team);
    }

    @Override
    public void requestAddTeamByInviteCode(Integer requestId, String inviteCode) {
        log.debug("{}", inviteCode);
        Team team = teamMapper.selectOne(
                new QueryWrapper<Team>().eq("invite_code", inviteCode)
        );
        if (team == null) {
            throw new NotFoundException("队伍不存在");
        }
        // 是否为自己创建的队伍
        if (team.getLeaderId().equals(requestId)) {
            throw new SimpleException(ApiInfo.TEAM_HAS_JIONED);
        }
        // 是否已加入
        boolean hasJoined = hasJoined(requestId, team.getId());
        if (hasJoined) {
            throw new SimpleException(ApiInfo.TEAM_HAS_JIONED);
        }

        // 向队长发送请求通知
        String message = "用户 {} 请求加入你的队伍：{}";
        Notification no = new Notification();
        no.setSenderId(requestId);
        no.setReceiverId(team.getLeaderId());
        no.setMessage(message);
        no.setTitle(NotifyTitle.REQUEST_ADD_TEAM);
        no.setType(NotifyType.REQUEST_ADD_TEAM);
        no.setDisplayPosition(NotifyPosition.TEAM);
        notiMapper.insert(no);

        elementSaver.save(no,
                NotificationElementType.USER.getKey(), requestId,
                NotificationElementType.TEAM.getKey(), team.getId()
        );
    }

    @Override
    public void respAddTeam(User leader, Long notificationId, Boolean isAgree) {
        log.debug("leaderId = {}, notifyId = {}, isAgree = {}", leader.getId(), notificationId, isAgree);
        // 获取通知
        Notification notification = getNotification(notificationId);
        if (isAgree) {
            // 从通知获取请求加入队伍的用户
            Integer requesterId = notification.getSenderId();
            // 获取通知元素，以获取队伍id
            QueryWrapper<NotificationElement> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("notification_id", notificationId).eq("seq", 1);
            NotificationElement element = elementMapper.selectOne(queryWrapper);
            // Math.toIntExact函数返回long参数的值。如果结果溢出int则抛出异常。
            Integer teamId = Math.toIntExact(element.getTargetId());

            // 是否已加入
            boolean hasJoined = hasJoined(requesterId, teamId);
            if (hasJoined) {
                throw new SimpleException(ApiInfo.TEAM_HAS_JIONED);
            }
            teamMapper.insertNewMember(teamId, requesterId);
        }
        // 设为已读
        notiMapper.hasRead(notificationId);
    }

    /**
     * 获取详细数据，将所有Team对象包装为TeamBo
     * @param teams
     * @return
     */
    private List<TeamBo> wrapTeamList(List<Team> teams) {
        List<TeamBo> res = new ArrayList<>(teams.size());
        for (Team team : teams) {
            TeamBo teamBoByTeamId = wrapTeam(team.getId());
            res.add(teamBoByTeamId);
        }
        return res;
    }

    /**
     * 获取 Team 数据 以及团队成员的部分用户信息
     * @param teamId
     * @return
     */
    private TeamBo wrapTeam(int teamId) {
        Team team = teamMapper.selectById(teamId);
        TeamBo teamBo = TeamBo.init(team);

        // 队长信息
        User leaderUser = userMapper.selectById(team.getLeaderId());
        leaderUser.setPassword(null);
        Student leader = studentMapper.selectStudentProfileById(team.getLeaderId());
        leaderUser.setUserInfo(leader);
        teamBo.setLeader(leaderUser);

        // 队员信息
        List<Integer> membersId = teamMapper.selectTeamMembersId(teamId);
        if (membersId.size() > 0) {
            // 优化：先从缓存查找
            List<User> memberUsers = new ArrayList<>(membersId.size());
            for (Integer id : membersId) {
                // 测试见RedisTest
                User user = (User) redis.opsForHash().get(RedisKey.USER_KEY, id);
                if (user == null) {
                    log.debug("查数据库");
                    user = userMapper.selectById(id);
                    user.setUserInfo(studentMapper.selectStudentProfileById(id));
                    redis.opsForHash().put(RedisKey.USER_KEY, id, user);
                }
                memberUsers.add(user);
            }
            teamBo.setMembers(memberUsers);
        } else {
            // 添加默认值，保证前端收到的数据不是null，
            // 避免出现Cannot read properties of undefined 异常
            teamBo.setMembers(new ArrayList<>(0));
        }
        return teamBo;
    }

    private Notification getNotification(Long notificationId) {
        Notification notification = notiMapper.selectById(notificationId);
        if (notification == null) {
            throw new NotFoundException("通知不存在");
        }
        return notification;
    }

    /**
     * 是否已加入
     * @param userId
     * @param teamId
     * @return
     */
    private boolean hasJoined(Integer userId, Integer teamId) {
        return teamMapper.selectExistMemberInTeam(userId, teamId) == 1;
    }
}
