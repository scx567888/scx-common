package cool.scx.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * a
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class Base64Utils {

    /**
     * cool.scx.base 64
     */
    private static final Base64.Encoder defaultBase64Encoder = Base64.getEncoder();

    /**
     * cool.scx.base 64
     */
    private static final Base64.Decoder defaultBase64Decoder = Base64.getDecoder();

    /**
     * 根据 字符串 获取(加密) BASE64
     *
     * @param str a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static byte[] encode(String str) {
        return encode(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 根据 BASE64 获取(解密) 字符串
     *
     * @param base64 a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static byte[] decode(String base64) {
        return decode(base64.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 根据 byte 获取(加密) BASE64
     *
     * @param bytes a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static byte[] encode(byte[] bytes) {
        return defaultBase64Encoder.encode(bytes);
    }

    /**
     * 根据 BASE64 获取(解密) 字符串
     *
     * @param base64 a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static byte[] decode(byte[] base64) {
        return defaultBase64Decoder.decode(base64);
    }

    /**
     * 根据 字符串 获取(加密) BASE64
     *
     * @param str a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String encodeToString(String str) {
        return encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 根据 BASE64 获取(解密) 字符串
     *
     * @param base64 a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String decodeToString(String base64) {
        return decodeToString(base64.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 根据 byte 获取(加密) BASE64
     *
     * @param bytes a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String encodeToString(byte[] bytes) {
        return new String(defaultBase64Encoder.encode(bytes), StandardCharsets.UTF_8);
    }

    /**
     * 根据 BASE64 获取(解密) 字符串
     *
     * @param base64 a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String decodeToString(byte[] base64) {
        return new String(defaultBase64Decoder.decode(base64), StandardCharsets.UTF_8);
    }

}
