package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.etherealss.common.enums.ApiInfo;
import pers.etherealss.common.enums.NotificationElementType;
import pers.etherealss.common.enums.NotifyType;
import pers.etherealss.common.enums.PublishState;
import pers.etherealss.common.exception.NotFoundException;
import pers.etherealss.common.exception.SimpleException;
import pers.etherealss.facade.NotificationFacade;
import pers.etherealss.manage.NotificationElementSaver;
import pers.etherealss.mapper.CompetitionMapper;
import pers.etherealss.mapper.NotificationElementMapper;
import pers.etherealss.mapper.NotificationMapper;
import pers.etherealss.mapper.OrganizationMapper;
import pers.etherealss.pojo.po.Notification;
import pers.etherealss.pojo.po.Organization;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.OrganizationService;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author etherealss
 * @since 2021-10-03
 */
@Slf4j
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Autowired
    private OrganizationMapper orgMapper;
    @Autowired
    private NotificationMapper notiMapper;
    @Autowired
    private NotificationElementMapper elementMapper;
    @Autowired
    private NotificationElementSaver elementSaver;
    @Autowired
    private NotificationFacade notiFacade;
    @Autowired
    private CompetitionMapper compMapper;

    @Override
    public List<Organization> getMyOrganizations(Integer officialId) {
        List<Organization> orgs = orgMapper.selectByOfficialId(officialId);
        QueryWrapper<Organization> query = new QueryWrapper<>();
        query.eq("manager_id", officialId);
        orgs.addAll(orgMapper.selectList(query));
        return orgs;
    }

    @Override
    public Msg<?> requestAddOrg(Integer userId, Integer orgId) {
        Organization org = orgMapper.selectById(orgId);
        if (org == null) {
            throw new NotFoundException("组织不存在");
        }
        String message = "用户 {} 请求加入组织：{}";
        Notification no = new Notification(NotifyType.REQUEST_ADD_ORG);
        no.setSenderId(userId);
        no.setReceiverId(org.getManagerId());
        no.setMessage(message);
        notiMapper.insert(no);

        elementSaver.save(no,
                NotificationElementType.USER.getKey(), userId,
                NotificationElementType.ORGANIZATION.getKey(), orgId
        );

        return Msg.ok();
    }

    @Override
    public Msg<?> respAddOrg(Integer managerId, Long notiId, Boolean isAgree) {
        log.debug("managerId = {}, notiId = {}, isAgree = {}", managerId, notiId, isAgree);
        // 获取通知
        Notification notification = notiFacade.getNotification4NotNull(notiId);
        // 从通知获取请求加入组织的用户
        Integer requesterId = notification.getSenderId();
        // 获取通知元素，以获取组织id
        Integer orgId = notiFacade.getElementTargetId4Int(notiId, 1);
        if (isAgree) {
            // 是否已加入
            boolean hasJoined = hasJoined(requesterId, orgId);
            if (hasJoined) {
                throw new SimpleException(ApiInfo.ORGANIZATION_HAS_JIONED);
            }
            // 添加新成员
            orgMapper.insertNewMember(orgId, requesterId);
        }
        // 设为已读
        notiMapper.hasRead(notiId);

        String message = isAgree ? "用户 {} 同意你加入组织：{}" : "用户 {} 拒绝你加入组织：{}";
        Notification no = new Notification(NotifyType.RESPONSE_ADD_ORG);
        no.setSenderId(managerId);
        no.setReceiverId(requesterId);
        no.setMessage(message);
        notiMapper.insert(no);

        elementSaver.save(no,
                NotificationElementType.USER.getKey(), managerId,
                NotificationElementType.ORGANIZATION.getKey(), orgId
        );
        return Msg.ok();
    }

    @Override
    public Msg<?> reviewComp(Integer managerId, Long notiId, Boolean isAgree) {
        log.debug("managerId = {}, notiId = {}, isAgree = {}", managerId, notiId, isAgree);
        // 获取通知
        Notification notification = notiFacade.getNotification4NotNull(notiId);
        // 从通知获取添加赛事的用户
        Integer requesterId = notification.getSenderId();
        // 获取通知元素，以获取赛事id
        Integer compId = notiFacade.getElementTargetId4Int(notiId, 1);

        if (isAgree) {
            // 修改为 管理员审核
            compMapper.updateState(compId, PublishState.REVIEWING);
        }

        // 设为已读
        notiMapper.hasRead(notiId);

        String message = isAgree ?
                "负责人 {} 审核并通过了你的赛事：{}" :
                "负责人 {} 审核但打回了你的赛事：{}，请重新修改！";
        Notification no = new Notification(NotifyType.RESPONSE_MANAGER_REVIEW_COMPETITION);
        no.setSenderId(managerId);
        no.setReceiverId(requesterId);
        no.setMessage(message);
        notiMapper.insert(no);

        elementSaver.save(no,
                NotificationElementType.USER.getKey(), managerId,
                NotificationElementType.COMPETITION.getKey(), compId
        );
        return Msg.ok();
    }

    private boolean hasJoined(Integer userId, Integer teamId) {
        return orgMapper.selectExistMemberInOrg(userId, teamId) == 1;
    }
}
