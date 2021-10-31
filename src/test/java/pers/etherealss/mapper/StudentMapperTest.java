package pers.etherealss.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.etherealss.pojo.po.Student;
import pers.etherealss.pojo.po.User;
import pers.etherealss.utils.InitializeUtil;

import java.util.Arrays;
import java.util.List;

@Slf4j
@DisplayName("StudentMapperTest测试")
@SpringBootTest
class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void testSelect() {
        Student student = studentMapper.selectById(1L);
        log.debug("{}", student);
    }

    @Test
    void testAdd() {
        User user = InitializeUtil.initClassInfo(User.class);
        log.debug("{}", user);
        Student student = InitializeUtil.initClassInfo(Student.class);
        BeanUtils.copyProperties(user, student);
        log.debug("{}", student);
        studentMapper.insert(student);
    }

    @Test
    void testselectStudentProfileById() {
        Student student = studentMapper.selectStudentProfileById(1);
        log.debug("{}", student);
    }

    @Test
    void testselectStudentsProfileById() {
        List<Integer> integers = Arrays.asList(1, 2, 3);
        List<Student> students = studentMapper.selectStudentsProfileById(integers);
        log.debug("{}", students);
    }
}