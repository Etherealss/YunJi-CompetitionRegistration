package pers.etherealss.service.impl;

import pers.etherealss.pojo.po.Student;
import pers.etherealss.mapper.StudentMapper;
import pers.etherealss.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author etherealss
 * @since 2021-10-03
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
