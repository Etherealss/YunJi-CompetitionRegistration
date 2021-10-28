package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @description 学生
 * @since 2021-10-03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("student")
public class Student extends UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 自我介绍
     */
    private String introduction;

    /**
     * 姓名
     */
    private String name;

    private Boolean sex;

    private Date birthday;
    /**
     * 入学年月
     */
    private Date enrollmentDate;

    /**
     * 院系
     */
    private String department;

    /**
     * 专业
     */
    private String major;

    /**
     * 班级号
     */
    private Integer classNo;

    /**
     * 学生学号
     */
    private String stuNo;

    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 学校名
     */
    private String schoolName;

}
