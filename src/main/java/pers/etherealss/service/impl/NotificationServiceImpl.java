package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.etherealss.common.enums.NotificationElementType;
import pers.etherealss.common.enums.NotifyPosition;
import pers.etherealss.common.enums.NotifyType;
import pers.etherealss.common.exception.NotFoundException;
import pers.etherealss.common.exception.UnsupportedOperationException;
import pers.etherealss.manage.NotificationElementSaver;
import pers.etherealss.manage.UserManage;
import pers.etherealss.mapper.*;
import pers.etherealss.pojo.bo.NotificationBo;
import pers.etherealss.pojo.bo.NotificationElementBo;
import pers.etherealss.pojo.po.*;
import pers.etherealss.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 * @author wtk
 * @since 2021-10-31
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
    @Autowired
    private NotificationMapper notiMapper;
    @Autowired
    private NotificationElementMapper elementMapper;
    @Autowired
    private NotificationElementSaver elementSaver;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserManage userManage;
    @Autowired
    private CompetitionMapper compMapper;
    @Autowired
    private OrganizationMapper orgMapper;

    @Override
    public void notifyAddTeanm2Leader(User sender, Team team) {
        String message = "用户 {} 请求加入你的队伍：{}";
        Notification notification = new Notification();
        notification.setSenderId(sender.getId());
        notification.setReceiverId(team.getLeaderId());
        notification.setMessage(message);
        notification.setTitle(NotifyType.REQUEST_ADD_TEAM.getTitle());
        notification.setType(NotifyType.REQUEST_ADD_TEAM.getType());
        notification.setDisplayPosition(NotifyPosition.TEAM);
        notiMapper.insert(notification);

        elementSaver.save(notification,
                NotificationElementType.USER.getKey(), sender.getId(),
                NotificationElementType.TEAM.getKey(), team.getId()
        );
    }

    @Override
    public void hasRead(Long notificationId) {
        notiMapper.hasRead(notificationId);
    }

    @Override
    public List<NotificationBo> getNotifications(Integer userId, String dispalyPosition) {
        QueryWrapper<Notification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver_id", userId)
                .eq("display_position", dispalyPosition)
                .orderByDesc("create_time");
        List<Notification> notifications = notiMapper.selectList(queryWrapper);

//        for (Notification notification : notifications) {
//            notification.setHasRead(true);
//            UpdateWrapper<Notification> updateWrapper = new UpdateWrapper<>();
//            updateWrapper.eq("id", notification.getId()).eq("has_read", false);
//            notiMapper.update(notification, updateWrapper);
//        }

        return wrapNotifications(notifications);
    }

    @Override
    public void notifyTeamRegister(Integer leaderId, Integer teamId, Integer competitionId) {
        Competition competition = compMapper.selectById(competitionId);
        if (competition == null) {
            throw new NotFoundException("赛事不存在");
        }

        Team team = teamMapper.selectById(teamId);
        if (team == null) {
            throw new NotFoundException("队伍不存在");
        }

        // 通知队员
        notifyTeamRegister2Member(teamId, competitionId, leaderId);
        // 通知举办方
        notifyTeamRegister2Manager(team, competition);
    }

    /**
     * 队伍报名 - 通知队员
     * @param teamId
     * @param competitionId
     * @param leaderId
     */
    private void notifyTeamRegister2Member(Integer teamId, Integer competitionId, Integer leaderId) {
        List<Integer> membersId = teamMapper.selectTeamMembersId(teamId);
        if (membersId.size() > 0) {
            String message = "你的队伍 {} 已报名比赛：{}";
            Notification noti = new Notification();
            noti.setMessage(message);
            noti.setSenderId(leaderId);
            noti.setTitle(NotifyType.REGISTER_COMPETITION.getTitle());
            noti.setType(NotifyType.REGISTER_COMPETITION.getType());
            noti.setDisplayPosition(NotifyPosition.COMPETITION);
            // 通知每一个队员
            for (Integer memberId : membersId) {
                noti.setReceiverId(memberId);
                notiMapper.insert(noti);
                elementSaver.save(noti,
                        NotificationElementType.TEAM.getKey(), teamId,
                        NotificationElementType.COMPETITION.getKey(), competitionId
                );
            }
        }
    }

    /**
     * 队伍报名 - 通知队员
     * @param team
     * @param competition
     */
    private void notifyTeamRegister2Manager(Team team, Competition competition) {
        String message = "队伍 {} 已报名比赛：{}";
        Notification noti = new Notification(NotifyType.NEW_REGISTER_COMPETITION);
        noti.setSenderId(team.getLeaderId());
        noti.setReceiverId(competition.getCreatorId());
        noti.setMessage(message);
        notiMapper.insert(noti);
        elementSaver.save(noti,
                NotificationElementType.TEAM.getKey(), team.getId(),
                NotificationElementType.COMPETITION.getKey(), competition.getId()
        );
    }

    /**
     * 将 Notification 包装为 NotificationBo ，添加SenderUser、ReceiverUser、Elements
     * @param notifications
     * @return
     */
    private List<NotificationBo> wrapNotifications(List<Notification> notifications) {
        List<NotificationBo> bos = new ArrayList<>(notifications.size());
        for (Notification notification : notifications) {
            NotificationBo bo = NotificationBo.init(notification);
            bo.setSender(userManage.getUserProfile(notification.getSenderId()));
            bo.setReceiver(userManage.getUserProfile(notification.getReceiverId()));
            bo.setElements(getNeBos(notification.getId()));
            bos.add(bo);
        }
        return bos;
    }

    /**
     * 获取NotificationElementBo列表
     * @param notificationId
     * @return
     */
    private List<NotificationElementBo<?>> getNeBos(Long notificationId) {
        List<NotificationElement> elements = elementMapper.selectByNotificationId(notificationId);
        return wrapNes(elements);
    }

    /**
     * 包装一组NotificationElement
     * @param elements
     * @return
     */
    private List<NotificationElementBo<?>> wrapNes(List<NotificationElement> elements) {
        List<NotificationElementBo<?>> elementBos = new ArrayList<>(elements.size());
        for (NotificationElement element : elements) {
            NotificationElementBo<?> notificationElementBo = this.initNe(element);
            elementBos.add(notificationElementBo);
        }
        return elementBos;
    }

    /**
     * 包装单个NotificationElement
     * @param element
     * @return
     */
    private NotificationElementBo<?> initNe(NotificationElement element) {
        NotificationElementBo<?> res = null;
        if (NotificationElementType.USER.getKey().equals(element.getTargetType())) {
            NotificationElementBo<User> bo = new NotificationElementBo<>();
            bo.setTarget(userManage.getUserProfile(Math.toIntExact(element.getTargetId())));
            res = bo;
        } else if (NotificationElementType.TEAM.getKey().equals(element.getTargetType())) {
            NotificationElementBo<Team> bo = new NotificationElementBo<>();
            bo.setTarget(teamMapper.selectById(element.getTargetId()));
            res = bo;
        } else if (NotificationElementType.COMPETITION.getKey().equals(element.getTargetType())) {
            NotificationElementBo<Competition> bo = new NotificationElementBo<>();
            bo.setTarget(compMapper.selectById(element.getTargetId()));
            res = bo;
        } else if (NotificationElementType.ORGANIZATION.getKey().equals(element.getTargetType())) {
            NotificationElementBo<Organization> bo = new NotificationElementBo<>();
            bo.setTarget(orgMapper.selectById(element.getTargetId()));
            res = bo;
        } else {
            log.warn("未知的NotificationElement#targetType:" + element.getTargetType());
            throw new UnsupportedOperationException("未知的NotificationElement#targetType:" + element.getTargetType());
        }
        BeanUtils.copyProperties(element, res);
        return res;
    }
}
