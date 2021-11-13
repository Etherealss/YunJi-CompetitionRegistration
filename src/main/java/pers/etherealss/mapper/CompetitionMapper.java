package pers.etherealss.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.Competition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author wtk
 * @since 2021-10-03
 */
@Repository
public interface CompetitionMapper extends BaseMapper<Competition> {


    void updateState(@Param("compId") Integer compId, @Param("state") Integer state);

}
