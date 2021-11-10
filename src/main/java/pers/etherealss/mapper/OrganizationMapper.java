package pers.etherealss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.Organization;

import java.util.List;

/**
 * @author etherealss
 * @since 2021-10-03
 */
@Repository
public interface OrganizationMapper extends BaseMapper<Organization> {

    /**
     * 获取用户所在的组织
     * @param officialId
     * @return
     */
    List<Organization> selectByOfficialId(Integer officialId);

}
