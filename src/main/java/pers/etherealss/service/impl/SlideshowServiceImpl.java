package pers.etherealss.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.etherealss.common.exception.NotFoundException;
import pers.etherealss.common.properties.ResourcePathProperties;
import pers.etherealss.mapper.SlideshowMapper;
import pers.etherealss.pojo.po.Slideshow;
import pers.etherealss.service.SlideshowService;
import pers.etherealss.utils.simple.FileUtil;

import java.io.*;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wtk
 * @since 2021-10-19
 */
@Service
public class SlideshowServiceImpl extends ServiceImpl<SlideshowMapper, Slideshow> implements SlideshowService {

    public static String LOCATION_INDEX = "index";

    @Autowired
    private SlideshowMapper slideshowMapper;

    @Autowired
    private ResourcePathProperties resourcePathProperties;

    @Override
    public List<Slideshow> getIndexSlideshow(int num) {
        List<Slideshow> indexSlidesshow = slideshowMapper.getIndexSlidesshow(LOCATION_INDEX);
        return indexSlidesshow;
    }

    @Override
    public InputStream showSlideshow(int id) throws IOException {
        Slideshow slideshow = slideshowMapper.selectById(id);
        if (slideshow == null) {
            throw new NotFoundException("轮播图不存在：id = " + id);
        }
        String filePath = resourcePathProperties.getSlideshow() + slideshow.getFilePath();
        BufferedInputStream inputStream = FileUtil.getInputStream(filePath);
        return inputStream;
    }
}
