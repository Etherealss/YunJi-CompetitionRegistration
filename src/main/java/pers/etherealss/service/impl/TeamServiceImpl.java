package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import pers.etherealss.mapper.StudentMapper;
import pers.etherealss.mapper.UserMapper;
import pers.etherealss.pojo.bo.TeamBo;
import pers.etherealss.pojo.po.Student;
import pers.etherealss.pojo.po.Team;
import pers.etherealss.mapper.TeamMapper;
import pers.etherealss.pojo.po.User;
import pers.etherealss.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author etherealss
 * @since 2021-10-03
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public TeamBo getTeamBo(int teamId) {
        return getTeamBoByTeamId(teamId);
    }

    @Override
    public List<TeamBo> getTeamBoByLeader(int leaderId) {
        QueryWrapper<Team> wrapper = new QueryWrapper<>();
        wrapper.eq("leader_id", leaderId);
        List<Team> teams = teamMapper.selectList(wrapper);
        List<TeamBo> res = new ArrayList<>(teams.size());
        for (Team team : teams) {
            TeamBo teamBoByTeamId = getTeamBoByTeamId(team.getId());
            res.add(teamBoByTeamId);
        }
        return res;
    }

    /**
     * 获取 Team 数据 以及团队成员的部分用户信息
     * @param teamId
     * @return
     */
    private TeamBo getTeamBoByTeamId(int teamId) {
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
            List<User> memberUsers = userMapper.selectBatchIds(membersId);
            List<Student> memberStudents = studentMapper.selectStudentsProfileById(membersId);
            for (int i = 0; i < memberUsers.size(); i++) {
                User memberUser = memberUsers.get(i);
                memberUser.setPassword(null);
                memberUser.setUserInfo(memberStudents.get(i));
            }
            teamBo.setMembers(memberUsers);
        } else {
            // 添加默认值，保证前端收到的数据不是null，
            // 避免出现Cannot read properties of undefined 异常
            teamBo.setMembers(new ArrayList<>(0));
        }
        return teamBo;
    }
}
