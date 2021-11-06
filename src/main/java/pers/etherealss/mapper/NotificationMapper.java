package pers.etherealss.mapper;

import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.Notification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author wtk
 * @since 2021-10-31
 */
@Repository
public interface NotificationMapper extends BaseMapper<Notification> {

    void hasRead(Long notificationId);

}
