package cool.scx.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 用于生成简单的随机数
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class RandomUtils {

    /**
     * 仅数字
     */
    private static final char[] NUMBER_POOL = "0123456789".toCharArray();

    /**
     * 数字和字母
     */
    private static final char[] NUMBER_AND_LETTER_POOL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /**
     * 获取随机的 Code
     * 注意!!! 此方法和 getUUID 不同 若需要获取 uuid 请使用 getUUID
     *
     * @param size       code 的长度
     * @param withLetter code 中是否包含字母
     * @return a {@link String} object
     */
    public static String randomString(int size, boolean withLetter) {
        var code = new StringBuilder();
        var pool = withLetter ? NUMBER_AND_LETTER_POOL : NUMBER_POOL;
        for (int i = 0; i < size; i = i + 1) {
            code.append(pool[ThreadLocalRandom.current().nextInt(pool.length)]);
        }
        return code.toString();
    }

    /**
     * 获取随机的 Code (包含字母和数字)
     *
     * @param size code 的长度
     * @return a
     */
    public static String randomString(int size) {
        return randomString(size, true);
    }

    /**
     * 取一个指定区间的随机数 (包含两端)
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机数
     */
    public static int randomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * 获取UUID
     *
     * @return a {@link String} object.
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

}
