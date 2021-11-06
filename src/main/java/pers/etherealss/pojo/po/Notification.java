package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
}
