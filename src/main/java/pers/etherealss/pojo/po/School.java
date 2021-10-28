package pers.etherealss.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wtk
 * @description 院校
 * @since 2021-10-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("school")
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String location;


}
