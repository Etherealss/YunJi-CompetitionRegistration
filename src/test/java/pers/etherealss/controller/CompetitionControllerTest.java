package pers.etherealss.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.etherealss.pojo.bo.PageBo;
import pers.etherealss.pojo.po.Competition;
import pers.etherealss.pojo.vo.Msg;

@Slf4j
@DisplayName("CompetitionControllerTest测试")
@SpringBootTest
class CompetitionControllerTest {

    @Autowired
    private CompetitionController competitionController;

    @Test
    void testGetPageCompetition() {
        Msg<PageBo<Competition>> msg = competitionController.getPageCompetition(1, null);
        log.debug("{}", JSONObject.toJSONString(msg));
    }

    @Test
    void testGetPageCompetitiionByOrder() {
        Msg<PageBo<Competition>> msg =
                competitionController.getPageCompetition(1, "time");
        log.debug("{}", JSONObject.toJSONString(msg));
    }
}