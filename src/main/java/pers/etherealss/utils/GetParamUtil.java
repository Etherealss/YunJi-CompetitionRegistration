package pers.etherealss.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author wtk
 * @description 获取前端请求参数
 * @date 2021-10-18
 */
@Slf4j
public class GetParamUtil {
    /**
     * 获取json参数，保存在JSONObject对象中
     * @param req
     * @return
     */
    public static JSONObject getJsonByJson(HttpServletRequest req) {
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(
                    req.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            JSONObject resultJson = JSONObject.parseObject(responseStrBuilder.toString());
            return resultJson;
        } catch (Exception e) {
            log.warn("解析json 失败 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过url获取参数，存到json中
     * @param req
     * @return
     */
    public static JSONObject getJsonByUrl(HttpServletRequest req) {
        try {
            String urlParams = req.getQueryString();
            if ("".equals(urlParams) || urlParams == null) {
                log.warn("getJsonByUrl 无参数");
                return null;
            }
            //切割 & ，得到一个个键值对
            String[] params = urlParams.split("&");
            JSONObject json = new JSONObject();
            for (String p : params) {
                //切割键值对，存到json中
                String[] kv = p.split("=");
                json.put(kv[0], kv[1]);
            }
            return json;
        } catch (Exception e) {
            log.warn("解析json 失败 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过form表单获取参数，封装为对象。可能会遗漏数据
     * @param req
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObjByForm(HttpServletRequest req, Class<T> clazz) {
        try {
            JSONObject json = new JSONObject();
            for (Field field : clazz.getDeclaredFields()) {
                String key = field.getName();
                String parameter = req.getParameter(key);
                json.put(key, parameter);
            }
            return json.toJavaObject(clazz);
        } catch (Exception ex) {
            log.warn("解析 formData 失败。Message：" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 通过表单获取数据，保存在json中
     * @param req
     * @return
     */
    public static JSONObject getJsonByForm(HttpServletRequest req) {
        JSONObject json = new JSONObject();
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            json.put(entry.getKey(), entry.getValue()[0]);
        }
        return json;
    }

    /**
     * 通过传入的json参数集合获取指定对象
     * @param jsonObject json参数集合
     * @param clazz
     * @param <T>
     * @return 封装的实例
     */
    public static <T> T getObjByParam(JSONObject jsonObject, Class<T> clazz) {
        try {
            JSONObject json = new JSONObject();
            for (Field field : clazz.getDeclaredFields()) {
                //获取对象属性名
                String key = field.getName();
                //获取属性值参数
                Object parameter = jsonObject.get(key);

                json.put(key, parameter);
            }
            //封装对象返回
            return json.toJavaObject(clazz);
        } catch (Exception e) {
            log.warn("封装对象失败", e);
        }
        return null;
    }
}
