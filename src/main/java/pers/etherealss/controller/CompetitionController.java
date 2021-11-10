package pers.etherealss.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pers.etherealss.common.enums.PageOrderBy;
import pers.etherealss.common.enums.PublishState;
import pers.etherealss.common.exception.ErrorParamException;
import pers.etherealss.common.exception.MissingParamException;
import pers.etherealss.pojo.bo.PageBo;
import pers.etherealss.pojo.po.Competition;
import pers.etherealss.pojo.po.User;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.CompetitionService;
import pers.etherealss.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 赛事
 * @author wtk
 * @since 2021-10-03
 */
@Slf4j
@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    /**
     * 单个页面显示的记录数
     */
    private static final int PAGE_SIZE = 8;

    @Autowired
    private CompetitionService compService;

    /*
       1.  使用;连接矩阵变量。请求路径写成：/cars/sell;low=34;brand=byd,audi,yd
       如果你的一个属性有多个值，有两种写法：
           1.  /cars/sell;low=34;brand=byd,audi,yd
           2.  /cars/sell;low=34;brand=byd;brand=audi;brand=yd
           第一个分号;之后的数据都是矩阵变量的参数
       2.  可以在多个地方写矩阵变量，在矩阵变量之后照常使用/即可：
           /cars/sell;low=34/1;brand=byd,audi,yd
       3.  可以声明是哪一个路径变量PathVariable后的矩阵变量：
           对于url：/boss/{bossId}/{empId}，访问的url为：/boss/1;age=20/2;age=18
           可以给@MatrixVariable指定pathVar参数：
           @MatrixVariable(value = "age", pathVal = "bossId")
    */

    /**
     * 获取分页数据
     * @param curPage
     * @return
     */
    @GetMapping("/public/pages/{curPage}")
    public Msg<PageBo<Competition>> getPageCompetition(
            @PathVariable(value = "curPage") int curPage,
            @MatrixVariable(value = "orderBy", pathVar = "curPage", required = false) String orderBy) {
        log.debug("获取分页数据：当前页curPage = {}, orderBy = {}", curPage, orderBy);
        PageBo<Competition> page = null;
        if (orderBy == null) {
            page = compService.getPage(curPage, PAGE_SIZE);
        } else if (PageOrderBy.TIME.equals(orderBy)) {
            page = compService.getPageByTime(curPage, PAGE_SIZE);
        } else {
            throw new ErrorParamException("不支持的orderBy格式");
        }
        return Msg.ok(page);
    }

    @GetMapping("/public/{id}")
    public Msg<Competition> getCompetition(@PathVariable Integer id) {
        log.debug("获取比赛信息：{}", id);
        if (id == null) {
            throw new MissingParamException("获取比赛信息参数缺失");
        }
        return compService.getOne4State(id, PublishState.PUBLISHED);
    }

    /**
     * 审核未发布的比赛时，获取待审核的比赛信息
     * @param id
     * @return
     */
    @Secured("ROLE_admin")
    @GetMapping("/review/{id}")
    public Msg<Competition> getCompetition4Review(@PathVariable Integer id) {
        log.debug("获取待审核的比赛信息：{}", id);
        if (id == null) {
            throw new MissingParamException("获取待审核的比赛信息参数缺失");
        }
        return compService.getOne4State(id, PublishState.REVIEWING);
    }

    @PostMapping("/create")
    public Msg<Competition> addCompetition(HttpServletRequest req,  @RequestBody Competition competition) {
        User user = TokenUtil.getUserByToken(req);
        log.debug("发布新比赛：{}", competition);
        compService.create(user, competition);
        return Msg.ok();
    }

    @Secured("ROLE_admin")
    @GetMapping("/review")
    public Msg<List<Competition>> get4Review() {
        Msg<List<Competition>> review = compService.getPage4Review();
        return review;
    }

}

