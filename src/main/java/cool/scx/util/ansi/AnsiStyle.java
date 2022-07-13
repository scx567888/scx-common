package cool.scx.util.ansi;

/**
 * Ansi 样式
 *
 * @author scx567888
 * @version 0.0.1
 */
public enum AnsiStyle implements AnsiElement {

    /**
     * 标准
     */
    NORMAL("0"),

    /**
     * 加粗
     */
    BOLD("1"),

    /**
     * 细线
     */
    FAINT("2"),

    /**
     * 斜体
     */
    ITALIC("3"),

    /**
     * 下划线
     */
    UNDERLINE("4");

    /**
     * a
     */
    private final String code;

    /**
     * <p>Constructor for AnsiStyle.</p>
     *
     * @param code a {@link String} object
     */
    AnsiStyle(String code) {
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
