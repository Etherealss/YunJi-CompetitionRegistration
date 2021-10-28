package pers.etherealss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.etherealss.mapper.StudentMapper;
import pers.etherealss.pojo.bo.TeamBo;
import pers.etherealss.pojo.po.Student;
import pers.etherealss.pojo.po.Team;
import pers.etherealss.mapper.TeamMapper;
import pers.etherealss.service.TeamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public TeamBo getTeamBo(Integer teamId) {
        Team team = teamMapper.selectById(teamId);
        Student leader = studentMapper.selectById(team.getLeaderId());
        TeamBo teamBo = TeamBo.init(team);
        List<Integer> membersId = teamMapper.selectTeamMembersId(teamId);
        List<Student> members = studentMapper.selectBatchIds(membersId);
        teamBo.setMembers(members);
        return teamBo;
    }
}
