package pers.etherealss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.etherealss.pojo.bo.PageBo;
import pers.etherealss.pojo.po.Competition;

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


}
