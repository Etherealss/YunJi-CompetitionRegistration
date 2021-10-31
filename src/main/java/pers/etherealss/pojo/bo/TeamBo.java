package pers.etherealss.pojo.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import pers.etherealss.pojo.po.Team;
import pers.etherealss.pojo.po.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wtk
 * @description 团队
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("team")
public class TeamBo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 队伍名称
     */
    private String name;

    private Date createTime;

    /**
     * 队长
     */
    private User leader;

    /**
     * 队员
     */
    private List<User> members;

    public static TeamBo init(Team team) {
        TeamBo teamBo = new TeamBo();
        BeanUtils.copyProperties(team, teamBo);
        return teamBo;
    }
}
