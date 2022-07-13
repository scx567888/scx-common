package cool.scx.util;

/**
 * <p>HexUtils class.</p>
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class HexUtils {

    /**
     * HEX 索引表 (String 类型)
     */
    private static final String HEX_CHAR_POOL_STR = "0123456789ABCDEF";

    /**
     * HEX 索引表
     */
    private static final char[] HEX_CHAR_POOL = HEX_CHAR_POOL_STR.toCharArray();

    /**
     * 十六进制转字节数组
     *
     * @param hex hex
     * @return r
     */
    public static byte[] toBytes(String hex) {
        var charArray = hex.toCharArray();
        var bytes = new byte[charArray.length / 2];
        int index = 0;
        while (index < bytes.length) {
            var highBit = HEX_CHAR_POOL_STR.indexOf(charArray[index * 2]);
            var lowBit = HEX_CHAR_POOL_STR.indexOf(charArray[index * 2 + 1]);
            bytes[index] = (byte) (highBit << 4 | lowBit);
            index = index + 1;
        }
        return bytes;
    }

    /**
     * 字节数组转十六进制
     *
     * @param bytes an array of {@link byte} objects.
     * @return a {@link java.lang.String} object.
     */
    public static String toHex(final byte[] bytes) {
        var chars = new char[bytes.length * 2];
        var index = 0;
        for (var b : bytes) {
            chars[index] = HEX_CHAR_POOL[b >>> 4 & 0xF];
            chars[index + 1] = HEX_CHAR_POOL[b & 0xF];
            index = index + 2;
        }
        return new String(chars);
    }

}
