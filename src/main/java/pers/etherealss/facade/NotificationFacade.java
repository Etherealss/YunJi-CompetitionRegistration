package pers.etherealss.facade;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.etherealss.common.exception.NotFoundException;
import pers.etherealss.mapper.NotificationElementMapper;
import pers.etherealss.mapper.NotificationMapper;
import pers.etherealss.pojo.po.Notification;
import pers.etherealss.pojo.po.NotificationElement;

/**
 * @author wtk
 * @date 2021-11-12
 */
@Slf4j
@Component
public class NotificationFacade {

    @Autowired
    private NotificationMapper notiMapper;
    @Autowired
    private NotificationElementMapper elementMapper;

    public Notification getNotification4NotNull(Long notificationId) {
        Notification notification = notiMapper.selectById(notificationId);
        if (notification == null) {
            throw new NotFoundException("通知不存在");
        }
        return notification;
    }

    public NotificationElement getElement(Long notificationId, int seq) {
        // 获取通知元素，以获取队伍id
        QueryWrapper<NotificationElement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notification_id", notificationId).eq("seq", seq);
        return elementMapper.selectOne(queryWrapper);
    }

    public Integer getElementTargetId4Int(Long notificationId, int seq) {
        // Math.toIntExact函数返回long参数的值。如果结果溢出int则抛出异常。
        return Math.toIntExact(this.getElement(notificationId, seq).getTargetId());
    }

    public void setHasRead(Long notificationId) {
        notiMapper.hasRead(notificationId);
    }

}
