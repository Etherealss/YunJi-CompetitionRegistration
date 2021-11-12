package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author wtk
 * @description 校内组织
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String profile;

    /**
     * 所属学校
     */
    private String affiliatedSchool;

    /**
     * 联系人
     */
    private Integer managerId;

    /**
     * 联系人
     */
    private Integer creatorId;

    /**
     * 联系人
     */
    private String managerName;

    /**
     * 联系方式
     */
    private String contactNumber;


}
