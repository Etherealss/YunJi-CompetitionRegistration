package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @description 比赛的某一个流程
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("process")
public class Process implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 赛事记录id
     */
    private Integer competitionId;

    /**
     * 次序，表示当前赛事的第几个流程
     */
    private Integer sequence;

    /**
     * 流程信息
     */
    private byte[] info;

    /**
     * 场地
     */
    private String venue;

    /**
     * 执行时间，如当前流程是“决赛”，则这里存储决赛时间
     */
    private Date executionTime;

    /**
     * 创建者
     */
    private Integer creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

}
