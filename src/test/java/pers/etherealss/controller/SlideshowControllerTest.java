package pers.etherealss.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.etherealss.pojo.po.Slideshow;
import pers.etherealss.pojo.vo.Msg;

import java.util.List;

@Slf4j
@DisplayName("SlideshowControllerTest测试")
@SpringBootTest
class SlideshowControllerTest {

    @Autowired
    private SlideshowController controller;
    @Test
    void testGetIndexSlideshow() {
        Msg<List<Slideshow>> indexSlideshow = controller.getIndexSlideshow(3);
        String s = JSONObject.toJSONString(indexSlideshow);
        log.debug("{}", s);
    }
}