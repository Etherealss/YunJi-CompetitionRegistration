package pers.etherealss.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.Student;
import pers.etherealss.pojo.po.Team;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
