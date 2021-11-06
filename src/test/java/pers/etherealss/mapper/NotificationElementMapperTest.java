package pers.etherealss.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("NotificationElementMapperTest测试")
@SpringBootTest
class NotificationElementMapperTest {

    @Autowired
    private NotificationElementMapper notificationElementMapper;
    @Test
    void testSelectTargetId() {
        Long aLong = notificationElementMapper.selectTargetId(1L, 1);
        log.debug("{}", aLong);
    }
}