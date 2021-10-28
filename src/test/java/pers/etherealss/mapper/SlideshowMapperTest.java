package pers.etherealss.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.etherealss.pojo.po.Slideshow;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("SlideshowMapperTest测试")
@SpringBootTest
class SlideshowMapperTest {

    @Autowired
    private SlideshowMapper slideshowMapper;
    @Test
    void testGetIndexSlidesshow() {
        Slideshow slideshow = slideshowMapper.selectById(1);
        log.debug("{}", slideshow);
    }
}