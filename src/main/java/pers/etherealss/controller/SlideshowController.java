package pers.etherealss.controller;


import com.github.javaparser.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.etherealss.pojo.po.Slideshow;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.service.SlideshowService;
import pers.etherealss.utils.ResponseUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 轮播图图片
 * @author wtk
 * @since 2021-10-19
 */
@RestController
@RequestMapping("/slideshows")
public class SlideshowController {

    @Autowired
    private SlideshowService slideshowService;

    @GetMapping("/index")
    public Msg<List<Slideshow>> getIndexSlideshow(@RequestParam(value = "num", defaultValue = "3") int num) {
        Log.trace("获取首页轮播图信息");
        List<Slideshow> indexSlideshow = slideshowService.getIndexSlideshow(num);
        return Msg.ok(indexSlideshow);
    }

    @GetMapping("/{slideId}")
    public void show(@PathVariable String slideId, HttpServletResponse response) throws IOException {
        Log.trace("显示轮播图图片");
        InputStream inputStream = slideshowService.showSlideshow(Integer.parseInt(slideId));
        ResponseUtil.sendFile(response, inputStream);
    }
}

