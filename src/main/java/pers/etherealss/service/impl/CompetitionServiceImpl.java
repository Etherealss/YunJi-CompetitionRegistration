package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.etherealss.common.strategy.page.PageContext;
import pers.etherealss.common.strategy.page.impl.QueryByDescTimeStragety;
import pers.etherealss.mapper.CompetitionMapper;
import pers.etherealss.pojo.bo.PageBo;
import pers.etherealss.pojo.po.Competition;
import pers.etherealss.service.CompetitionService;

/**
 * @author wtk
 * @description
 * @since 2021-10-03
 */
@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {

    @Autowired
    private CompetitionMapper competitionMapper;

    @Override
    public PageBo<Competition> getPage(int curPage, int offset) {
        PageContext<Competition> pageContext = new PageContext<>(competitionMapper);
        PageBo<Competition> pageBo = pageContext.bulidPage(curPage, offset);
        return pageBo;
    }

    @Override
    public PageBo<Competition> getPageByTime(int curPage, int offset) {
        PageContext<Competition> pageContext = new PageContext<>(competitionMapper, new QueryByDescTimeStragety<>());
        PageBo<Competition> pageBo = pageContext.bulidPage(curPage, offset, "create_time");
        return pageBo;
    }
}
