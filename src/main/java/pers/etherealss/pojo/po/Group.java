package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wtk
 * @description 某一个流程的分组
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer processId;

    /**
     * 第几组
     */
    private Integer sequence;


}
