package cool.scx.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 简易计时器类 用来计算启动时间
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class StopWatch {

    /**
     * 纳秒和毫秒直接的换算单位
     */
    private static final long MILLI_SCALE = 1000 * 1000;

    /**
     * 纳秒和秒之间的换算单位
     */
    private static final double SECOND_SCALE = 1000 * MILLI_SCALE;

    /**
     * 池
     */
    private static final Map<String, Long> START_TIME_MAP = new HashMap<>();

    /**
     * 启动计时器
     *
     * @param name a {@link java.lang.String} object.
     */
    public static void start(String name) {
        if (START_TIME_MAP.get(name) == null) {
            START_TIME_MAP.put(name, System.nanoTime());
        } else {
            throw new IllegalArgumentException("定时器 [ " + name + " ] 已存在!!! 若要重置定时器,请使用 reset() !!!");
        }
    }

    /**
     * 创建随机名称
     *
     * @return a
     */
    public static String start() {
        var randomName = RandomUtils.randomString(6);
        start(randomName);
        return randomName;
    }

    /**
     * a
     *
     * @param name a
     */
    public static void reset(String name) {
        START_TIME_MAP.put(name, System.nanoTime());
    }

    /**
     * 停止计时并返回时间差 单位微毫秒
     *
     * @param name a {@link java.lang.String} object.
     * @return 时间差
     */
    public static long stopToNanos(String name) {
        var startTime = START_TIME_MAP.get(name);
        return startTime != null ? System.nanoTime() - startTime : -1;
    }

    /**
     * 停止计时并返回时间差 单位毫秒
     *
     * @param name a {@link java.lang.String} object.
     * @return 时间差
     */
    public static long stopToMillis(String name) {
        return nanosToMillis(stopToNanos(name));
    }

    /**
     * 停止计时并返回时间差 单位秒
     *
     * @param name a {@link java.lang.String} object.
     * @return 时间差
     */
    public static double stopToSeconds(String name) {
        return nanosToSeconds(stopToNanos(name));
    }

    /**
     * 纳秒转毫秒
     *
     * @param duration d
     * @return d
     */
    private static long nanosToMillis(long duration) {
        return duration / MILLI_SCALE;
    }

    /**
     * 纳秒转秒 (这里四舍五入 并精确到后两位数)
     *
     * @param duration d
     * @return d
     */
    private static double nanosToSeconds(long duration) {
        return BigDecimal.valueOf(duration / SECOND_SCALE).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

}
