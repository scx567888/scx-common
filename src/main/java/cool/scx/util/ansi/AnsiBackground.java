package cool.scx.util.ansi;

/**
 * 背景色
 *
 * @author scx567888
 * @version 0.0.1
 */
public enum AnsiBackground implements AnsiElement {

    /**
     * a
     */
    DEFAULT("49"),

    /**
     * a
     */
    BLACK("40"),

    /**
     * a
     */
    RED("41"),

    /**
     * a
     */
    GREEN("42"),

    /**
     * a
     */
    YELLOW("43"),

    /**
     * a
     */
    BLUE("44"),

    /**
     * a
     */
    MAGENTA("45"),

    /**
     * a
     */
    CYAN("46"),

    /**
     * a
     */
    WHITE("47"),

    /**
     * a
     */
    BRIGHT_BLACK("100"),

    /**
     * a
     */
    BRIGHT_RED("101"),

    /**
     * a
     */
    BRIGHT_GREEN("102"),

    /**
     * a
     */
    BRIGHT_YELLOW("103"),

    /**
     * a
     */
    BRIGHT_BLUE("104"),

    /**
     * a
     */
    BRIGHT_MAGENTA("105"),

    /**
     * a
     */
    BRIGHT_CYAN("106"),

    /**
     * a
     */
    BRIGHT_WHITE("107");

    /**
     * a
     */
    private final String code;

    /**
     * <p>Constructor for AnsiBackground.</p>
     *
     * @param code a {@link String} object
     */
    AnsiBackground(String code) {
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
