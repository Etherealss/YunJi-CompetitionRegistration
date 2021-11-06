package pers.etherealss.pojo.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 通知信息的一个元素
 * 比如：用户 张三 请求加入你的队伍：你说的都队
 * 用户名和队伍名都应该是一个对象，它们就是NotificationElement
 * @author wtk
 * @since 2021-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("notification_element")
public class NotificationElementBo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 占位符次序
     */
    private Integer seq;

    /**
     * 目标的主键
     */
    private T target;

    /**
     * 用来表示target的类型。比如target_id是userId，那么key就是user
     */
    private String key;

}
