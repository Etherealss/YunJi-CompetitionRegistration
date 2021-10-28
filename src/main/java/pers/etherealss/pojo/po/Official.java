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
 * @description 官方人员
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("official")
public class Official extends UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;

    /**
     * 姓名
     */
    private String name;

    private Boolean sex;

    private Date birthday;
    /**
     * 联系方式
     */
    private String contact;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 自我介绍
     */
    private String introduction;
}
