package pers.etherealss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.etherealss.pojo.po.Slideshow;

import java.util.List;

/**
 * @author wtk
 * @since 2021-10-19
 */
@Repository
public interface SlideshowMapper extends BaseMapper<Slideshow> {

    /**
     * 获取轮播图
     * @param location
     * @return
     */
    List<Slideshow> getIndexSlidesshow(@Param("location") String location);
}
