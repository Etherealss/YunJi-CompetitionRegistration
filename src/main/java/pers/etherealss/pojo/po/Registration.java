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
 * @description 报名
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("registration")
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer teamId;

    private Integer competitionId;

    private Date createTime;

    /** 报名是否已取消 */
    private Boolean hasCancelled;

    @TableField(exist = false)
    private Team team;
    @TableField(exist = false)
    private Competition competition;

    public Registration(Integer teamId, Integer competitionId) {
        this.teamId = teamId;
        this.competitionId = competitionId;
    }

    public Registration() {
    }
}
