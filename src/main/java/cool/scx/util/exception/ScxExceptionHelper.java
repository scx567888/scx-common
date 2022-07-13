package cool.scx.util.exception;


import cool.scx.functional.ScxHandlerE;
import cool.scx.functional.ScxHandlerRE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ExecutionException;

/**
 * a
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class ScxExceptionHelper {

    /**
     * 包装 异常
     *
     * @param handler a
     * @param <T>     a
     * @return a
     */
    public static <T> T wrap(ScxHandlerRE<T, ?> handler) {
        try {
            return handler.handle();
        } catch (Throwable throwable) {
            throw new ScxWrappedRuntimeException(throwable);
        }
    }

    /**
     * 包装 异常
     *
     * @param handler a
     */
    public static void wrap(ScxHandlerE<?> handler) {
        try {
            handler.handle();
        } catch (Throwable throwable) {
            throw new ScxWrappedRuntimeException(throwable);
        }
    }

    /**
     * 忽略异常 发生异常时返回 null
     *
     * @param handler a
     * @param <T>     a
     * @return a
     */
    public static <T> T ignore(ScxHandlerRE<T, ?> handler) {
        try {
            return handler.handle();
        } catch (Throwable throwable) {
            return null;
        }
    }

    /**
     * 忽略异常 发生异常时返回默认值
     *
     * @param handler    a
     * @param <T>        a
     * @param defaultVal a T object
     * @return a
     */
    public static <T> T ignore(ScxHandlerRE<T, ?> handler, T defaultVal) {
        try {
            return handler.handle();
        } catch (Throwable throwable) {
            return defaultVal;
        }
    }

    /**
     * 忽略异常
     *
     * @param handler a
     */
    public static void ignore(ScxHandlerE<?> handler) {
        try {
            handler.handle();
        } catch (Throwable ignored) {

        }
    }

    /**
     * 执行的操作是否有异常 (有异常时不打印信息)
     *
     * @param exceptionScxHandlerVE a
     * @return a
     */
    public static boolean noException(ScxHandlerE<?> exceptionScxHandlerVE) {
        try {
            exceptionScxHandlerVE.handle();
            return true;
        } catch (Throwable throwable) {
            return false;
        }
    }

    /**
     * 解包包装后的异常
     *
     * @param throwable a
     * @return a
     */
    public static Throwable getRootCause(Throwable throwable) {
        if (throwable instanceof ScxWrappedRuntimeException || throwable instanceof ExecutionException) {
            return getRootCause(throwable.getCause());
        }
        return throwable;
    }

    /**
     * 获取 jdk 内部默认实现的堆栈跟踪字符串
     *
     * @param throwable t
     * @return t
     */
    public static String getStackTraceString(Throwable throwable) {
        var stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.getBuffer().toString();
    }

}
