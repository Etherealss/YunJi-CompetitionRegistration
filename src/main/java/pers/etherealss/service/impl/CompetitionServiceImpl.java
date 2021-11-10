package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.etherealss.common.enums.ApiInfo;
import pers.etherealss.common.enums.PublishState;
import pers.etherealss.common.exception.NotFoundException;
import pers.etherealss.common.exception.SimpleException;
import pers.etherealss.common.strategy.page.PageContext;
import pers.etherealss.common.strategy.page.impl.QueryByDescTimeStragety;
import pers.etherealss.mapper.CompetitionMapper;
import pers.etherealss.mapper.OfficialMapper;
import pers.etherealss.mapper.OrganizationMapper;
import pers.etherealss.pojo.bo.PageBo;
import pers.etherealss.pojo.po.Competition;
import pers.etherealss.pojo.po.Official;
import pers.etherealss.pojo.po.Organization;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.CompetitionService;

import java.util.List;

/**
 * @author wtk
 * @description
 * @since 2021-10-03
 */
@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements CompetitionService {

    @Autowired
    private CompetitionMapper compMapper;
    @Autowired
    private OfficialMapper offMapper;
    @Autowired
    private OrganizationMapper orgMapper;

    @Override
    public PageBo<Competition> getPage(int curPage, int offset) {
        PageContext<Competition> pageContext = new PageContext<>(compMapper);
        PageBo<Competition> pageBo = pageContext.bulidPage(curPage, offset);
        return pageBo;
    }

    @Override
    public PageBo<Competition> getPageByTime(int curPage, int offset) {
        PageContext<Competition> pageContext = new PageContext<>(compMapper, new QueryByDescTimeStragety<>());
        PageBo<Competition> pageBo = pageContext.bulidPage(curPage, offset, "create_time");
        return pageBo;
    }

    @Override
    public Msg<List<Competition>> getPage4Review() {
        QueryWrapper<Competition> m = new QueryWrapper<>();
        List<Competition> cs = compMapper.selectList(m.eq("state", 1));
        return Msg.ok(cs);
    }

    @Override
    public Msg<Competition> getOne4Review(Integer id) {
        QueryWrapper<Competition> m = new QueryWrapper<>();
        m.eq("state", 1).eq("id", id);
        return Msg.ok(compMapper.selectOne(m));
    }

    @Override
    public void create(User user, Competition competition) {
        Official official = offMapper.selectById(user.getId());
        competition.setCreatorId(user.getId());
        competition.setManagerName(official.getName());
        competition.setConsultingPhone(official.getContact());
        Organization organization = orgMapper.selectById(competition.getOrganizationId());
        if (organization == null) {
            throw new NotFoundException("组织不存在");
        }
        competition.setOrganizationName(organization.getName());
        competition.setCreatorId(user.getId());
        competition.setState(1);
        compMapper.insert(competition);
    }

    @Override
    public Msg<Competition> getOne4State(Integer id, int state) {
        Competition competition = compMapper.selectById(id);
        if (competition == null) {
            throw new NotFoundException("没有比赛：" + id + " 的数据");
        }
        if (competition.getState() == state) {
            return Msg.ok(competition);
        }
        if (competition.getState() == PublishState.REVIEWING) {
            // 审核中
            throw new SimpleException(ApiInfo.IS_REVIEWING);
        } else if (competition.getState() == PublishState.DRAFT) {
            // 草稿
            throw new SimpleException(ApiInfo.IS_DRAFT);
        } else {
            // 已发布
            throw new SimpleException(ApiInfo.HAS_PUBLISHED);
        }
    }
}
