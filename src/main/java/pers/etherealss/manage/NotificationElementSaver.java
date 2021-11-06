package pers.etherealss.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.etherealss.mapper.NotificationElementMapper;
import pers.etherealss.pojo.po.Notification;
import pers.etherealss.pojo.po.NotificationElement;

/**
 * 简化繁琐的 NotificationElement 保存代码
 * @author wtk
 * @date 2021-11-01
 */
@Component
public class NotificationElementSaver {

    @Autowired
    private NotificationElementMapper mapper;

    /**
     * @param notification
     * @param args 奇数位是key，偶数位是value
     */
    public void save(Notification notification, Object... args) {
        Long id = notification.getId();
        int seq = 0;
        for (int i = 0; i < args.length; i += 2) {
            NotificationElement element = new NotificationElement();
            element.setNotificationId(id);
            element.setSeq(seq);
            seq++;
            element.setTargetType((String) args[i]);
            element.setTargetId(Long.parseLong(String.valueOf(args[i + 1])));
            mapper.insert(element);
        }
    }
}
