package cool.scx.util;

import java.util.Scanner;

/**
 * 控制台工具类
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class ConsoleUtils {

    /**
     * Constant <code>scanner</code>
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * <p>readLine.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public static String readLine() {
        return scanner.nextLine();
    }

}
