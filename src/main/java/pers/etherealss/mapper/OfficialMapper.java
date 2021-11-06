package pers.etherealss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.Official;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
@Repository
public interface OfficialMapper extends BaseMapper<Official> {

    /**
     * 查找学生的部分数据，用户预览和展示
     * @param officialId
     * @return
     */
    Official selectOfficialProfileById(@Param("officialId") Integer officialId);

}
