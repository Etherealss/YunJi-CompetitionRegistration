package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.etherealss.common.enums.PublishState;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @description 赛事
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("competition")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 赛事名称
     */
    private String name;

    /**
     * 简介
     */
    private String profile;

    /**
     * 流程介绍
     */
    private String processIntroduce;

    /**
     * 举办组织id
     */
    private Integer organizationId;

    /**
     * 举办组织名称
     */
    private String organizationName;

    /**
     * 负责人姓名
     */
    private String managerName;

    /**
     * 咨询电话
     */
    private String consultingPhone;

    /**
     * 比赛范围：学院、全校、全省等
     */
    private String scope;

    /**
     * 队伍最小人数
     */
    private Integer minTeamMember;

    /**
     * 队伍最大人数
     */
    private Integer maxTeamMember;

    /**
     * 创建者
     */
    private Integer creatorId;

    /**
     * 记录添加时间
     */
    private Date createTime;

    /**
     * 记录修改时间
     */
    private Date updateTime;

    /**
     * 是否为草稿
     */
    private Integer state;

    /**
     * 是否为草稿
     * @return
     */
    public boolean isDraft() {
        return state == PublishState.DRAFT;
    }

    /**
     * 是否正在审核
     * @return
     */
    public boolean isReviewing() {
        return state == PublishState.DRAFT;
    }

    /**
     * 是否正在审核
     * @return
     */
    public boolean isPublished() {
        return state == PublishState.PUBLISHED;
    }
}
