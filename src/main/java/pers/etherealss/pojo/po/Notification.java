package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.etherealss.common.enums.NotifyType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @since 2021-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发送者
     */
    private Integer senderId;

    /**
     * 接收者
     */
    private Integer receiverId;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知信息
     */
    private String message;

    /**
     * 展示位置，如system表示在系统消息展示
     */
    private String displayPosition;
    /**
     * 通知类型
     */
    private String type;

    /**
     * 是否已读
     */
    private Boolean hasRead;

    private Date createTime;

    public Notification(NotifyType type) {
        this.title = type.getTitle();
        this.type = type.getType();
        this.displayPosition = type.getPosition();
    }

    public Notification(Integer senderId, Integer receiverId, String message, NotifyType type) {
        this(type);
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public Notification() {
    }

    public Notification setId(Long id) {
        this.id = id;
        return this;
    }

    public Notification setSenderId(Integer senderId) {
        this.senderId = senderId;
        return this;
    }

    public Notification setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public Notification setTitle(String title) {
        this.title = title;
        return this;
    }

    public Notification setMessage(String message) {
        this.message = message;
        return this;
    }

    public Notification setDisplayPosition(String displayPosition) {
        this.displayPosition = displayPosition;
        return this;
    }

    public Notification setType(String type) {
        this.type = type;
        return this;
    }

    public Notification setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
        return this;
    }

    public Notification setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
