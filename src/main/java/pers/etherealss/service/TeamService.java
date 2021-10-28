package pers.etherealss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.etherealss.pojo.bo.TeamBo;
import pers.etherealss.pojo.po.Team;

import java.io.Serializable;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
public interface TeamService extends IService<Team> {

    /**
     * 获取队伍信息
     * @param id
     * @return
     */
    TeamBo getTeamBo(Integer id);
}
