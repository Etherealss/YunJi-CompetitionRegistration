package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.etherealss.mapper.OrganizationMapper;
import pers.etherealss.pojo.po.Organization;
import pers.etherealss.service.OrganizationService;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author etherealss
 * @since 2021-10-03
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Autowired
    private OrganizationMapper orgMapper;

    @Override
    public List<Organization> getMyOrganizations(Integer officialId) {
        return orgMapper.selectByOfficialId(officialId);
    }
}
