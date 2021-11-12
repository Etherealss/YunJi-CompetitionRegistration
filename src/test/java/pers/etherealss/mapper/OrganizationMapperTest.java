package pers.etherealss.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DisplayName("OrganizationMapperTest测试")
@SpringBootTest
class OrganizationMapperTest {

    @Autowired
    private OrganizationMapper oMapper;
    @Test
    void testSelectByOfficialId() {
    }

    @Test
    void testselectExistMemberInOrg() {
        Long aLong = oMapper.selectExistMemberInOrg(5, 1);
        log.debug("{}", aLong);
    }

    @Transactional
    @Test
    void testinsertNewMember() {
        oMapper.insertNewMember(5,5);
    }
}