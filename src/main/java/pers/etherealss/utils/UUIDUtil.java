package pers.etherealss.utils;

import java.util.UUID;

/**
 * @author wtk
 * @date 2021-11-01
 */
public class UUIDUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
