package pers.etherealss.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DisplayName("NotificationMapperTest测试")
@SpringBootTest
class NotificationMapperTest {

    @Autowired
    private NotificationMapper notificationMapper;
    @Test
    void testHasRead() {
        notificationMapper.hasRead(1L);
    }
}