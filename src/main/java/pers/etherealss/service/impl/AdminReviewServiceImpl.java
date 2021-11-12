package pers.etherealss.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.etherealss.common.enums.*;
import pers.etherealss.manage.NotificationElementSaver;
import pers.etherealss.mapper.CompetitionMapper;
import pers.etherealss.mapper.NotificationElementMapper;
import pers.etherealss.mapper.NotificationMapper;
import pers.etherealss.pojo.po.Competition;
import pers.etherealss.pojo.po.Notification;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.AdminReviewService;

/**
 * @author wtk
 * @date 2021-11-09
 */
@Slf4j
@Service
public class AdminReviewServiceImpl implements AdminReviewService {

    @Autowired
    private CompetitionMapper compMapper;
    @Autowired
    private NotificationMapper notiMapper;
    @Autowired
    private NotificationElementMapper elementMapper;
    @Autowired
    private NotificationElementSaver elementSaver;

    @Override
    public Msg<?> reviewCompetition(User admin, Competition comp, Boolean action) {
        if (action) {
            comp.setState(PublishState.PUBLISHED);
            compMapper.updateById(comp);
        } else {
            // 审核不通过，变成草稿状态
            comp.setState(PublishState.DRAFT);
            compMapper.updateById(comp);
        }
        String message =
                action ? "你发布的赛事 {} 已通过审核，发布成功！" : "你发布赛事 {} 审核不通过，请重新修改！";
        Notification notification = new Notification();
        notification.setSenderId(admin.getId());
        notification.setReceiverId(comp.getCreatorId());
        notification.setMessage(message);
        notification.setTitle(NotifyType.REVIEW_COMPETITION.getTitle());
        notification.setType(NotifyType.REVIEW_COMPETITION.getType());
        notification.setDisplayPosition(NotifyPosition.SYSTEM);
        notiMapper.insert(notification);

        elementSaver.save(notification,
                NotificationElementType.COMPETITION.getKey(), comp.getId()
        );
        return Msg.ok();
    }
}
