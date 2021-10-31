package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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

    @TableField(exist = false)
    private User sender;

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
     * 通知类型
     */
    private String type;

    private Date createTime;


}
