package pers.etherealss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.etherealss.pojo.po.Organization;
import pers.etherealss.pojo.vo.Msg;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
public interface OrganizationService extends IService<Organization> {

    List<Organization> getMyOrganizations(Integer officialId);

    Msg<?> requestAddOrg(Integer userId, Integer orgId);

    Msg<?> respAddOrg(Integer userId, Long notiId, Boolean action);

    Msg<?> reviewComp(Integer managerId, Long notiId, Boolean action);
}
