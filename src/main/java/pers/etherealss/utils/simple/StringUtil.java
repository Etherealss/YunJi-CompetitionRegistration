package pers.etherealss.utils.simple;

/**
 * @author wtk
 * @description
 * @date 2021-10-05
 */
public class StringUtil {

    public static boolean isBlank(CharSequence str) {
        return (str == null) || ((str.length()) == 0);
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    /**
     * 比较两个字符串（大小写不敏感）
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    /**
     * 判断两个字符串是否相等
     * @param str1
     * @param str2
     * @param ignoreCase
     * @return
     */
    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            // 只有两个都为null才判断相等
            return str2 == null;
        }
        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false;
        }

        if (ignoreCase) {
            return str1.toString().equalsIgnoreCase(str2.toString());
        } else {
            return str1.toString().contentEquals(str2);
        }
    }
}
