package cool.scx.util;

/**
 * String工具类
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class StringUtils {

    /**
     * 创建重复字符串 (带分隔符) 拓展了 {@link java.lang.String#repeat(int)} 无法添加分隔符的功能
     *
     * @param str       源字符串
     * @param delimiter 分隔符
     * @param count     重复次数
     * @return 结果
     */
    public static String repeat(String str, String delimiter, int count) {
        if (count == 0) {
            return "";
        }
        var element = str + delimiter;
        var result = element.repeat(count);
        return result.substring(0, result.length() - delimiter.length());
    }

    /**
     * 校验字符串是否不为 null 且不全为空白 (空格 " ")
     *
     * @param str 待检查的字符串
     * @return a boolean.
     */
    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 校验字符串是否为 null 或全为空白 (空格 " ")
     *
     * @param str a {@link java.lang.Object} object.
     * @return a boolean.
     */
    public static boolean isBlank(String str) {
        return str == null || str.isBlank();
    }

    /**
     * 校验字符串是否不为 null 并且不为空字符串 ("")
     *
     * @param str s
     * @return s
     */
    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 校验字符串是否为 null 或为空字符串 ("")
     *
     * @param str s
     * @return s
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
