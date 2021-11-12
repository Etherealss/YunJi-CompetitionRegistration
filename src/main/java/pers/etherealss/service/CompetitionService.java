package pers.etherealss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.etherealss.pojo.bo.PageBo;
import pers.etherealss.pojo.po.Competition;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
public interface CompetitionService extends IService<Competition> {

    PageBo<Competition> getPage(int curPage, int offset);

    PageBo<Competition> getPageByTime(int curPage, int offset);

    /**
     * 获取审核数据
     * @return
     */
    Msg<PageBo<Competition>> getPage4Review(int curPage, int offset);

    /**
     * 获取审核数据
     * @return
     * @param id
     */
    Msg<Competition> getOne4Review(Integer id);

    void create(User user, Competition competition);

    Msg<Competition> getOne4State(Integer id, int state);
}
