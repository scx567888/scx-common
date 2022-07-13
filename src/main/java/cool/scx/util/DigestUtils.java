package cool.scx.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 摘要算法工具类<br>
 * 只是针对 jdk 中自带的 {@link java.security.MessageDigest} 进行的简单封装<br>
 * 注意 : SHA 和 MD5 为单向散列函数,
 * 只适用于防篡改 或单项加密(如密码) 等 .
 * 如有加密后需要解密的需求 , 建议使用 {@link cool.scx.util.CryptoUtils}
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class DigestUtils {

    /**
     * 缓冲区大小
     */
    private static final int CACHE_LENGTH = 256 * 1024;

    /**
     * <p>digest.</p>
     *
     * @param data       a {@link java.lang.String} object
     * @param digestType s
     * @return a {@link java.lang.String} object
     */
    private static String digest(final String data, String digestType) {
        return digest(data != null ? data.getBytes(StandardCharsets.UTF_8) : null, digestType);
    }

    /**
     * <p>digest.</p>
     *
     * @param data       an array of {@link byte} objects
     * @param digestType s
     * @return a {@link java.lang.String} object
     */
    private static String digest(final byte[] data, String digestType) {
        Objects.requireNonNull(data, "Data must not be empty !!!");
        return HexUtils.toHex(getDigest(digestType).digest(data));
    }

    /**
     * <p>digest.</p>
     *
     * @param data       a {@link java.io.File} object
     * @param digestType s
     * @return a {@link java.lang.String} object
     * @throws java.io.IOException if any.
     */
    private static String digest(final File data, String digestType) throws IOException {
        Objects.requireNonNull(data, "Data must not be empty !!!");
        var digest = getDigest(digestType);
        var buffer = new byte[CACHE_LENGTH];
        int read;
        try (var file = new RandomAccessFile(data, "r")) {
            while ((read = file.read(buffer, 0, CACHE_LENGTH)) != -1) {
                digest.update(buffer, 0, read);
            }
        }
        return HexUtils.toHex(digest.digest());
    }

    /**
     * <p>sha1.</p>
     *
     * @param data a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String sha1(final String data) {
        return digest(data, "SHA-1");
    }

    /**
     * <p>sha1.</p>
     *
     * @param data an array of {@link byte} objects
     * @return a {@link java.lang.String} object
     */
    public static String sha1(final byte[] data) {
        return digest(data, "SHA-1");
    }

    /**
     * <p>sha1.</p>
     *
     * @param data a {@link java.io.File} object
     * @return a {@link java.lang.String} object
     * @throws java.io.IOException if any.
     */
    public static String sha1(final File data) throws IOException {
        return digest(data, "SHA-1");
    }

    /**
     * <p>sha256.</p>
     *
     * @param data a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String sha256(final String data) {
        return digest(data, "SHA-256");
    }

    /**
     * <p>sha256.</p>
     *
     * @param data an array of {@link byte} objects
     * @return a {@link java.lang.String} object
     */
    public static String sha256(final byte[] data) {
        return digest(data, "SHA-256");
    }

    /**
     * <p>sha256.</p>
     *
     * @param data a {@link java.io.File} object
     * @return a {@link java.lang.String} object
     * @throws java.io.IOException if any.
     */
    public static String sha256(final File data) throws IOException {
        return digest(data, "SHA-256");
    }

    /**
     * <p>sha384.</p>
     *
     * @param data a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String sha384(final String data) {
        return digest(data, "SHA-384");
    }

    /**
     * <p>sha384.</p>
     *
     * @param data an array of {@link byte} objects
     * @return a {@link java.lang.String} object
     */
    public static String sha384(final byte[] data) {
        return digest(data, "SHA-384");
    }

    /**
     * <p>sha384.</p>
     *
     * @param data a {@link java.io.File} object
     * @return a {@link java.lang.String} object
     * @throws java.io.IOException if any.
     */
    public static String sha384(final File data) throws IOException {
        return digest(data, "SHA-384");
    }

    /**
     * <p>sha512.</p>
     *
     * @param data a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String sha512(final String data) {
        return digest(data, "SHA-512");
    }

    /**
     * <p>sha512.</p>
     *
     * @param data an array of {@link byte} objects
     * @return a {@link java.lang.String} object
     */
    public static String sha512(final byte[] data) {
        return digest(data, "SHA-512");
    }

    /**
     * <p>sha512.</p>
     *
     * @param data a {@link java.io.File} object
     * @return a {@link java.lang.String} object
     * @throws java.io.IOException if any.
     */
    public static String sha512(final File data) throws IOException {
        return digest(data, "SHA-512");
    }

    /**
     * <p>md5.</p>
     *
     * @param data a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String md5(final String data) {
        return digest(data, "MD5");
    }

    /**
     * <p>md5.</p>
     *
     * @param data an array of {@link byte} objects
     * @return a {@link java.lang.String} object
     */
    public static String md5(final byte[] data) {
        return digest(data, "MD5");
    }

    /**
     * 计算 md5
     *
     * @param data a {@link java.io.File} object
     * @return a {@link java.lang.String} object
     * @throws java.io.IOException if any.
     */
    public static String md5(final File data) throws IOException {
        return digest(data, "MD5");
    }

    /**
     * <p>getDigest.</p>
     *
     * @param digestType s
     * @return a {@link java.security.MessageDigest} object
     */
    private static MessageDigest getDigest(final String digestType) {
        try {
            return MessageDigest.getInstance(digestType);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalArgumentException(exception);
        }
    }

}
