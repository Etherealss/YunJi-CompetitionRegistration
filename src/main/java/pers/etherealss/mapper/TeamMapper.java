package pers.etherealss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.Team;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
@Repository
public interface TeamMapper extends BaseMapper<Team> {

    /**
     * 查询队员
     * @param teamId
     * @return
     */
    List<Integer> selectTeamMembersId(@Param("teamId") Integer teamId);

    /**
     * 查询某一个队员所在的所有队伍
     * @param userId
     * @return
     */
    List<Team> selectTeamsByMemberId(@Param("userId") Integer userId);

    /**
     * 检查用户是否在队伍中
     * @param userId
     * @param teamId
     * @return
     */
    Long selectExistMemberInTeam(@Param("userId") Integer userId, @Param("teamId") Integer teamId);

    /**
     * 队伍添加新成员
     * @param teamId
     * @param memberId
     */
    void insertNewMember(@Param("teamId") Integer teamId, @Param("memberId") Integer memberId);
}
