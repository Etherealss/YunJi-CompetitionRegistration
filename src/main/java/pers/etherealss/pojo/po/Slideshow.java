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
 * @since 2021-10-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("slideshow")
public class Slideshow implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 图片访问路径
     */
    private String filePath;

    /**
     * 跳转链接
     */
    private String targetUrl;

    /**
     * 说明
     */
    private String info;

    private Date createTime;

    private Date updateTime;

    private Boolean isInUse;

    private String showLocation;

    public Boolean getIsInUse() {
        return isInUse;
    }
}

