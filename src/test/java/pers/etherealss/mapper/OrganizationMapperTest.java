package pers.etherealss.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@DisplayName("OrganizationMapperTest测试")
@SpringBootTest
class OrganizationMapperTest {

    @Autowired
    private OrganizationMapper oMapper;
    @Test
    void testSelectByOfficialId() {
    }
}