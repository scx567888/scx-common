package cool.scx.util;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 命名方法转换工具类 <br>
 * 提供 驼峰 匈牙利 短横线 及 蛇形命名法的互相转换
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class CaseUtils {

    /**
     * 切割驼峰命名法的正则表达式
     *
     * @see <a href="https://stackoverflow.com/a/7594052">https://stackoverflow.com/a/7594052</a>
     */
    private static final Pattern CAMEL_PATTERN = Pattern.compile("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");

    /**
     * 转换为驼峰命名法 getNameByAge
     *
     * @param str s
     * @return r
     */
    public static String toCamel(String str) {
        //就是将匈牙利命名法的首字母小写而已
        return makeFirstCharLowerCase(toPascal(str));
    }

    /**
     * 转换为匈牙利命名法 GetNameByAge
     *
     * @param str s
     * @return r
     */
    public static String toPascal(String str) {
        return Arrays.stream(getSourceStrings(str)).map(s -> makeFirstCharUpperCase(s.toLowerCase())).collect(Collectors.joining());
    }

    /**
     * 转换为短横线命名法 get-name-by-age
     *
     * @param str s
     * @return r
     */
    public static String toKebab(String str) {
        return Arrays.stream(getSourceStrings(str)).map(String::toLowerCase).collect(Collectors.joining("-"));
    }

    /**
     * 转换为蛇形命名法 get_name_by_age
     *
     * @param str s
     * @return r
     */
    public static String toSnake(String str) {
        return Arrays.stream(getSourceStrings(str)).map(String::toLowerCase).collect(Collectors.joining("_"));
    }

    /**
     * 推断原来的命名是啥 并返回切割后的源字符串
     *
     * @param str 源字符串
     * @return 分割后的数组
     */
    private static String[] getSourceStrings(String str) {
        if (StringUtils.isBlank(str)) {
            return new String[0];
        }
        //判断原字符串是什么类型
        if (str.contains("_")) { // 以下划线分割 如 a_b
            return str.split("_");
        } else if (str.contains("-")) { //以短横线分割 如 a-b
            return str.split("-");
        } else { //其余情形全当作 驼峰进行处理
            return CAMEL_PATTERN.split(str);
        }
    }

    /**
     * 将字符串的第一个字母大写 如 abc 转换为 Abc
     *
     * @param string s
     * @return s
     */
    private static String makeFirstCharUpperCase(String string) {
        var charArray = string.toCharArray();
        if ('a' <= charArray[0] && charArray[0] <= 'z') {
            charArray[0] = (char) (charArray[0] ^ 32);
        }
        return new String(charArray);
    }

    /**
     * 将字符串的第一个字母小写 如 ABC 转换为 aBC
     *
     * @param string s
     * @return s
     */
    private static String makeFirstCharLowerCase(String string) {
        var charArray = string.toCharArray();
        if ('A' <= charArray[0] && charArray[0] <= 'Z') {
            charArray[0] = (char) (charArray[0] ^ 32);
        }
        return new String(charArray);
    }

}