package pers.etherealss.utils;

import com.alibaba.fastjson.JSONObject;
import pers.etherealss.pojo.vo.Msg;
import pers.etherealss.utils.simple.IoUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @author wtk
 * @description 向前端发送响应数据
 */
public class ResponseUtil {

    public static <T> void send(HttpServletResponse response, Msg<T> msg) throws IOException {
        //发送给客户端
        send(response, JSONObject.toJSONString(msg));
    }

    public static void send(HttpServletResponse response, JSONObject jsonObject) throws IOException {
        send(response, jsonObject.toJSONString());
    }

    public static void send(HttpServletResponse response, String jsonString) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }

    /**
     * 输出文件
     * @param response
     * @param inputStream
     * @throws IOException
     */
    public static void sendFile(HttpServletResponse response, InputStream inputStream) throws IOException {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            // 创建数据缓冲区
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                out.write(b, 0, length);
                out.flush();
            }
        } finally {
            IoUtil.close(out);
            IoUtil.close(inputStream);
        }
    }
}
