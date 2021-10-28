package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wtk
 * @description 团队的分组比赛结果
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("team_group_result")
public class TeamGroupResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer competitionId;

    private Integer groupId;

    private Integer teamId;

    @TableField(exist = false)
    private Team team;
    @TableField(exist = false)
    private Group group;
    @TableField(exist = false)
    private Competition competition;

    /**
     * 分组比赛结束时间
     */
    private Date finishTime;

    /**
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 组内排名
     */
    private Integer rank;

    /**
     * 记录“胜/负”或者最终得分
     */
    private String resultInfo;

    private String introduce;


}
