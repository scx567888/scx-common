package cool.scx.util.ansi;

/**
 * 前景色
 *
 * @author scx567888
 * @version 0.0.1
 */
public enum AnsiColor implements AnsiElement {

    /**
     * 默认 (一般是白色)
     */
    DEFAULT("39"),

    /**
     * 黑色
     */
    BLACK("30"),

    /**
     * 红色
     */
    RED("31"),

    /**
     * 绿色
     */
    GREEN("32"),

    /**
     * 黄色
     */
    YELLOW("33"),

    /**
     * 蓝色
     */
    BLUE("34"),

    /**
     * 紫色
     */
    MAGENTA("35"),

    /**
     * 青色
     */
    CYAN("36"),

    /**
     * 白色 (其实是有点发灰)
     */
    WHITE("37"),

    /**
     * 亮黑色 ( 真的存在这种颜色吗 ? )
     */
    BRIGHT_BLACK("90"),

    /**
     * 亮红色
     */
    BRIGHT_RED("91"),

    /**
     * 亮绿色
     */
    BRIGHT_GREEN("92"),

    /**
     * 亮黄色
     */
    BRIGHT_YELLOW("93"),

    /**
     * 亮蓝色
     */
    BRIGHT_BLUE("94"),

    /**
     * 亮紫色
     */
    BRIGHT_MAGENTA("95"),

    /**
     * 亮青色
     */
    BRIGHT_CYAN("96"),

    /**
     * 亮白色 (这个是真正的白色)
     */
    BRIGHT_WHITE("97");

    /**
     * a
     */
    private final String code;

    /**
     * <p>Constructor for AnsiColor.</p>
     *
     * @param code a {@link String} object
     */
    AnsiColor(String code) {
        this.code = code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String code() {
        return this.code;
    }

}
