package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.etherealss.common.enums.ApiInfo;
import pers.etherealss.common.enums.NotificationElementType;
import pers.etherealss.common.enums.NotifyType;
import pers.etherealss.common.enums.PublishState;
import pers.etherealss.common.exception.NotFoundException;
import pers.etherealss.common.exception.SimpleException;
import pers.etherealss.common.strategy.page.PageContext;
import pers.etherealss.common.strategy.page.impl.CompetitionQueryStragety;
import pers.etherealss.manage.NotificationElementSaver;
import pers.etherealss.mapper.*;
import pers.etherealss.pojo.bo.PageBo;
import pers.etherealss.pojo.po.*;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.CompetitionService;

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
    @Autowired
    private NotificationMapper notiMapper;
    @Autowired
    private NotificationElementMapper elementMapper;
    @Autowired
    private NotificationElementSaver elementSaver;

    @Override
    public PageBo<Competition> getPage(int curPage, int offset) {
        PageContext<Competition> pageContext = new PageContext<>(compMapper);
        PageBo<Competition> pageBo = pageContext.bulidPage(curPage, offset);
        return pageBo;
    }

    @Override
    public PageBo<Competition> getPageByTime(int curPage, int offset) {
        PageContext<Competition> pageContext = new PageContext<>(compMapper, new CompetitionQueryStragety<>());
        PageBo<Competition> pageBo = pageContext.bulidPage(curPage, offset);
        return pageBo;
    }

    @Override
    public Msg<PageBo<Competition>> getPage4Review(int curPage, int offset) {
        PageContext<Competition> pageContext = new PageContext<>(compMapper, queryWrapper -> {
            // ???????????????state???1??????????????????????????????
            queryWrapper.orderByAsc("state");
            // ??????????????????????????????
            queryWrapper.orderByDesc("update_time", "create_time");
        });
        PageBo<Competition> pageBo = pageContext.bulidPage(curPage, offset);

        return Msg.ok(pageBo);
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
            throw new NotFoundException("???????????????");
        }
        competition.setOrganizationName(organization.getName());


        if (!organization.getManagerId().equals(user.getId())) {
            competition.setState(PublishState.MANAGER_REVIEWING);
            compMapper.insert(competition);
            Notification n = new Notification(NotifyType.MANAGER_REVIEW_COMPETITION);
            n.setSenderId(user.getId());
            n.setReceiverId(organization.getManagerId());
            String msg = "???????????? {} ??????????????????{}????????????";
            n.setMessage(msg);
            notiMapper.insert(n);
            elementSaver.save(n,
                    NotificationElementType.ORGANIZATION.getKey(), organization.getId(),
                    NotificationElementType.COMPETITION.getKey(), competition.getId()
            );
        } else {
            // ???????????????????????????????????????????????????
            competition.setState(PublishState.REVIEWING);
            compMapper.insert(competition);
        }
    }

    @Override
    public Msg<Competition> getOne4State(Integer id, int state) {
        Competition competition = compMapper.selectById(id);
        if (competition == null) {
            throw new NotFoundException("???????????????" + id + " ?????????");
        }
        if (competition.getState() == state) {
            return Msg.ok(competition);
        }
        if (competition.getState() == PublishState.REVIEWING) {
            // ?????????
            throw new SimpleException(ApiInfo.IS_REVIEWING);
        } else if (competition.getState() == PublishState.DRAFT) {
            // ??????
            throw new SimpleException(ApiInfo.IS_DRAFT);
        } else {
            // ?????????
            throw new SimpleException(ApiInfo.HAS_PUBLISHED);
        }
    }
}
