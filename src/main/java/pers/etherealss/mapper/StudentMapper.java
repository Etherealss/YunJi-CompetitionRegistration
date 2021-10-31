package pers.etherealss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.Student;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
@Repository
public interface StudentMapper extends BaseMapper<Student> {

    /**
     * 查找学生的部分数据，用户预览和展示
     * @param studentId
     * @return
     */
    Student selectStudentProfileById(@Param("studentId") Integer studentId);

    /**
     * 查找学生的部分数据，用于预览和展示
     * @param ids
     * @return
     */
    List<Student> selectStudentsProfileById(@Param("ids") List<Integer> ids);
}
