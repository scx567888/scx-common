package cool.scx.util.ansi;

import cool.scx.util.RandomUtils;

import java.util.*;

/**
 * 向控制台打印彩色
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class Ansi {

    /**
     * 颜色列表
     */
    private static final AnsiColor[] ANSI_COLORS = AnsiColor.values();

    /**
     * 合并字符
     */
    private static final String ENCODE_JOIN = ";";

    /**
     * 起始字符
     */
    private static final String ENCODE_START = "\033[";

    /**
     * 结束字符
     */
    private static final String ENCODE_END = "m";

    /**
     * 重置
     */
    private static final String RESET = "0;" + AnsiColor.DEFAULT.code();

    /**
     * 下一个颜色 做内部索引使用
     */
    private static int nextPrintColor = RandomUtils.randomNumber(0, ANSI_COLORS.length);

    /**
     * 是否开启颜色
     */
    private static boolean enabled = detectIfAnsiCapable();

    /**
     * 待输出的数据
     */
    private final List<Object> elements = new ArrayList<>();

    /**
     * 私有构造函数
     */
    private Ansi() {
    }

    /**
     * <p>out.</p>
     *
     * @return a {@link cool.scx.util.ansi.Ansi} object
     */
    public static Ansi out() {
        return new Ansi();
    }

    /**
     * 返回当前是否使用 ansi
     *
     * @return 是否
     */
    public static boolean isEnabled() {
        return enabled;
    }

    /**
     * 设置是否使用 ansi
     *
     * @param enabled 开启或不开启
     */
    public static void setEnabled(boolean enabled) {
        Ansi.enabled = enabled;
    }

    /**
     * Encode a single {@link cool.scx.util.ansi.AnsiElement} if output is enabled.
     *
     * @param element the element to encode
     * @return the encoded element or an empty string
     */
    public static String encode(AnsiElement element) {
        if (isEnabled()) {
            return ENCODE_START + element + ENCODE_END;
        }
        return "";
    }

    /**
     * Create a new ANSI string from the specified elements. Any {@link cool.scx.util.ansi.AnsiElement}s will
     * be encoded as required.
     *
     * @param elements the elements to encode
     * @return a string of the encoded elements
     */
    public static String toString(Object... elements) {
        StringBuilder sb = new StringBuilder();
        if (isEnabled()) {
            buildEnabled(sb, elements);
        } else {
            buildDisabled(sb, elements);
        }
        return sb.toString();
    }

    /**
     * a
     *
     * @param sb       a
     * @param elements a
     */
    private static void buildEnabled(StringBuilder sb, Object[] elements) {
        boolean writingAnsi = false;
        boolean containsEncoding = false;
        for (var element : elements) {
            if (element instanceof AnsiElement) {
                containsEncoding = true;
                if (!writingAnsi) {
                    sb.append(ENCODE_START);
                    writingAnsi = true;
                } else {
                    sb.append(ENCODE_JOIN);
                }
                sb.append(((AnsiElement) element).code());
            } else {
                if (writingAnsi) {
                    sb.append(ENCODE_END);
                    writingAnsi = false;
                }
                sb.append(element);
            }
        }
        if (containsEncoding) {
            sb.append(writingAnsi ? ENCODE_JOIN : ENCODE_START);
            sb.append(RESET);
            sb.append(ENCODE_END);
        }
    }

    /**
     * a
     *
     * @param sb       a
     * @param elements a
     */
    private static void buildDisabled(StringBuilder sb, Object[] elements) {
        for (var element : elements) {
            if (!(element instanceof AnsiElement) && element != null) {
                sb.append(element);
            }
        }
    }

    /**
     * 检测是否支持 ansi todo 此处检测应该更加严谨
     *
     * @return ansi
     */
    private static boolean detectIfAnsiCapable() {
        try {
            if (System.console() == null) {
                return false;
            }
            var OPERATING_SYSTEM_NAME = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
            return !OPERATING_SYSTEM_NAME.contains("win");
        } catch (Throwable ex) {
            return false;
        }
    }

    /**
     * 过滤参数 每个类型的参数只留一种
     *
     * @param elements  a
     * @param elements2 b
     * @return an array of {@link cool.scx.util.ansi.AnsiElement} objects
     */
    private static AnsiElement[] filterAnsiElement(AnsiElement[] elements, AnsiElement... elements2) {
        AnsiElement ansiColor = null;
        AnsiElement ansiBackground = null;
        Set<AnsiStyle> ansiStyleSet = new LinkedHashSet<>();

        var ansiElements = new AnsiElement[elements.length + elements2.length];
        System.arraycopy(elements, 0, ansiElements, 0, elements.length);
        System.arraycopy(elements2, 0, ansiElements, elements.length, elements2.length);

        for (var element : ansiElements) {
            if (element instanceof AnsiColor || element instanceof Ansi8BitColor) {
                ansiColor = element;
            } else if (element instanceof AnsiBackground || element instanceof Ansi8BitBackground) {
                ansiBackground = element;
            } else if (element instanceof AnsiStyle) {
                ansiStyleSet.add((AnsiStyle) element);
            }
        }
        var l = new ArrayList<AnsiElement>();
        if (ansiColor != null) {
            l.add(ansiColor);
        }
        if (ansiBackground != null) {
            l.add(ansiBackground);
        }
        l.addAll(ansiStyleSet);
        return l.toArray(AnsiElement[]::new);
    }

    /**
     * 红色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi red(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.RED));
    }

    /**
     * 绿色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi green(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.GREEN));
    }

    /**
     * 亮青色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi brightCyan(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BRIGHT_CYAN));
    }

    /**
     * 蓝色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi blue(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BLUE));
    }

    /**
     * 青色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi cyan(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.CYAN));
    }

    /**
     * 亮蓝色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi brightBlue(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BRIGHT_BLUE));
    }

    /**
     * 亮紫色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi brightMagenta(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BRIGHT_MAGENTA));
    }

    /**
     * 亮红色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi brightRed(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BRIGHT_RED));
    }

    /**
     * 亮绿色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi brightGreen(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BRIGHT_GREEN));
    }

    /**
     * 黑色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi black(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BLACK));
    }

    /**
     * 亮黑色 ( 真的存在这种颜色吗 ? )
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi brightBlack(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BRIGHT_BLACK));
    }

    /**
     * 亮黄色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi brightYellow(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BRIGHT_YELLOW));
    }

    /**
     * 黄色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi yellow(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.YELLOW));
    }

    /**
     * 紫色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi magenta(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.MAGENTA));
    }

    /**
     * 白色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi white(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.WHITE));
    }

    /**
     * 亮白色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi brightWhite(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.BRIGHT_WHITE));
    }

    /**
     * 默认颜色
     *
     * @param o            a {@link java.lang.Object} object.
     * @param ansiElements AnsiElements
     * @return Ansi 方便链式调用
     */
    public Ansi defaultColor(Object o, AnsiElement... ansiElements) {
        return add(o, filterAnsiElement(ansiElements, AnsiColor.DEFAULT));
    }

    /**
     * 换行
     *
     * @return a {@link cool.scx.util.ansi.Ansi} object.
     */
    public Ansi ln() {
        add(System.lineSeparator());
        return this;
    }

    /**
     * 向控制台打印输出 颜色根据内部计数器自动读取
     *
     * @param o            a {@link java.lang.String} object.
     * @param ansiElements AnsiElements
     * @return a {@link cool.scx.util.ansi.Ansi} object.
     */
    public Ansi color(Object o, AnsiElement... ansiElements) {
        if (nextPrintColor >= ANSI_COLORS.length) {
            nextPrintColor = 0;
        }
        add(o, filterAnsiElement(ansiElements, ANSI_COLORS[nextPrintColor]));
        nextPrintColor = nextPrintColor + 1;
        return this;
    }

    /**
     * a
     *
     * @param o            a
     * @param ansiElements a
     * @return a
     */
    public Ansi add(Object o, AnsiElement... ansiElements) {
        elements.addAll(Arrays.asList(ansiElements));
        elements.add(o);
        return this;
    }

    /**
     * <p>print.</p>
     */
    public void print() {
        System.out.print(toString(this.elements.toArray()));
    }

    /**
     * 清除当前的 所有 语句
     */
    public void clear() {
        elements.clear();
    }

    /**
     * <p>println.</p>
     */
    public void println() {
        ln();
        print();
    }

}
