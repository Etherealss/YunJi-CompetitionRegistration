package pers.etherealss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.NotificationElement;

import java.util.List;

/**
 * @author wtk
 * @since 2021-11-01
 */
@Repository
public interface NotificationElementMapper extends BaseMapper<NotificationElement> {

    List<NotificationElement> selectByNotificationId(@Param("notificationId") Long notificationId);

    Long selectTargetId(@Param("notificationId") Long notificationId, @Param("seq") int seq);
}
