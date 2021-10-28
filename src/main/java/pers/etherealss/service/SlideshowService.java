package pers.etherealss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.etherealss.pojo.po.Slideshow;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author wtk
 * @since 2021-10-19
 */
public interface SlideshowService extends IService<Slideshow> {

    /**
     * 获取首页的轮播图数据
     * @param num
     * @return
     */
    List<Slideshow> getIndexSlideshow(int num);

    InputStream showSlideshow(int id) throws IOException;
}
