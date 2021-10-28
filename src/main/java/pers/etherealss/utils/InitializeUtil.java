package pers.etherealss.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @author wtk
 * @description
 * @date 2021-10-03
 */
public class InitializeUtil {

    private static final Logger LOG = LoggerFactory.getLogger(InitializeUtil.class);

    /**
     * 初始化类数据
     * @param cls
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T initClassInfo(Class<T> cls) {
        T bean = null;
        try {
            bean = cls.newInstance();
            Method[] methods = cls.getDeclaredMethods();
            Field[] fields = cls.getDeclaredFields();

            for (Field field : fields) {
                String fieldSetName = parSetName(field.getName());
                if (!checkSetMet(methods, fieldSetName)) {
                    continue;
                }
                Method fieldSetMet = cls.getMethod(fieldSetName,
                        field.getType());

                fieldSet(bean, fieldSetMet, field);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("initClassInfo调用异常");
        }
        return bean;
    }


    /**
     * 生成随机数据
     * @param type
     * @return
     */
    private static Object getRandomInfo(String type) {
        try {
            switch (type) {
                case "String":
                    // 15位随机字符串
                    return UUID.randomUUID().toString().substring(15);
                case "Date":
                    return new Date();
                case "Long":
                    return (long) (Math.random() * 100000);
                case "Integer":
                case "int":
                    return (int) (Math.random() * 1000);
                case "Double":
                    return Math.random() * 100;
                case "Boolean":
                    double a = Math.random();
                    return a > 0.5;
                default:
            }
        } catch (Exception e) {
           LOG.error("初始化数据失败:" + type, e);
        }
        return null;
    }

    /**
     * 拼接在某属性的 set方法
     * @param fieldName
     * @return String
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == StringPool.UNDERSCORE.charAt(0)) {
            startIndex = 1;
        }
        return "set"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * 判断是否存在某属性的 set方法
     * @param methods
     * @param fieldSetMet
     * @return boolean
     */
    private static boolean checkSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {
            if (fieldSetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 调用某个set方法set数据
     * @param bean
     * @param fieldSetMet
     * @param field
     * @throws Exception
     */
    private static void fieldSet(Object bean, Method fieldSetMet, Field field) throws Exception {
        String fieldType = field.getType().getSimpleName();
        Object value = getRandomInfo(fieldType);
        fieldSetMet.invoke(bean, value);
    }
}
