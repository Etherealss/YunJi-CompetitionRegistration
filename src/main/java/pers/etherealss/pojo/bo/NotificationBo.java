package pers.etherealss.pojo.bo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import pers.etherealss.pojo.po.Notification;
import pers.etherealss.pojo.po.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wtk
 * @date 2021-11-01
 */
@Data
public class NotificationBo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 发送者
     */
    private User sender;

    /**
     * 接收者
     */
    private User receiver;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知信息
     */
    private String message;

    /**
     * 通知类型
     */
    private String type;

    /**
     * 是否已读
     */
    private Boolean hasRead;

    private Date createTime;

    /**
     * 展示位置，如system表示在系统消息展示
     */
    private String displayPosition;

    /**
     * 通知信息中的元素
     */
    private List<NotificationElementBo<?>> elements;

    public NotificationBo() {
    }

    public static NotificationBo init(Notification notification) {
        NotificationBo bo = new NotificationBo();
        BeanUtils.copyProperties(notification, bo);
        return bo;
    }
}
