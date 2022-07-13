package cool.scx.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 文件 操作类
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class FileUtils {

    /**
     * 文件大小格式化 正则表达式
     */
    private final static Pattern DISPLAY_SIZE_PATTERN = Pattern.compile("^([\\d.]+) *([a-zA-Z]{0,2})$");

    /**
     * 将 long 类型的文件大小 格式化(转换为人类可以看懂的形式)
     * 如 1024 转换为 1KB
     *
     * @param size a long.
     * @return a {@link java.lang.String} object.
     */
    public static String longToDisplaySize(long size) {
        if (size <= 0) {
            return "0";
        }
        var units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 将 格式化后的大小转换为 long
     * 如将 1KB 转换为 1024
     *
     * @param str 待转换的值 如 5MB 13.6GB
     * @return a long.
     */
    public static long displaySizeToLong(String str) {
        var matcher = DISPLAY_SIZE_PATTERN.matcher(str);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(str + " : 无法转换为 long !!!");
        }
        var amount = Double.parseDouble(matcher.group(1));
        var units = matcher.group(2);
        var s = switch (units) {
            case "", "B" -> 1L;
            case "KB" -> 1024L;
            case "MB" -> 1024 * 1024L;
            case "GB" -> 1024 * 1024 * 1024L;
            case "TB" -> 1024 * 1024 * 1024 * 1024L;
            default -> throw new IllegalArgumentException(units + " : 未知的数据单位 !!!");
        };
        return (long) (amount * s);
    }

    /**
     * 删除文件或文件夹(会删除文件树中所有内容)
     *
     * @param start   a
     * @param options a
     * @throws java.io.IOException a
     */
    public static void delete(Path start, DeleteOption... options) throws IOException {
        var info = new DeleteOption.Info(options);
        var visitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                var dontNeedDelete = info.excludeRoot() && Files.isSameFile(start, dir);
                if (!dontNeedDelete) {
                    Files.delete(dir);
                }
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            Files.walkFileTree(start, visitor);
        } catch (NoSuchFileException ignore) {

        }
    }

    /**
     * 本质上就是调用 {@link java.nio.file.Files#move(Path, Path, CopyOption...)} ,但是在之前会创建不存在的父目录
     *
     * @param source  a
     * @param target  a
     * @param options a
     * @throws java.io.IOException a
     */
    public static void move(Path source, Path target, CopyOption... options) throws IOException {
        Files.createDirectories(target.getParent());
        Files.move(source, target, options);
    }

    /**
     * 本质上就是调用 {@link java.nio.file.Files#copy(Path, Path, CopyOption...)} ,但是在之前会创建不存在的父目录
     *
     * @param source  a
     * @param target  a
     * @param options a
     * @throws java.io.IOException a
     */
    public static void copy(Path source, Path target, CopyOption... options) throws IOException {
        Files.createDirectories(target.getParent());
        Files.copy(source, target, options);
    }

    /**
     * 本质上就是调用 {@link java.nio.file.Files#write(Path, byte[], OpenOption...)} ,但是在之前会创建不存在的父目录
     *
     * @param path    a
     * @param bytes   a
     * @param options a
     * @throws java.io.IOException a
     */
    public static void write(Path path, byte[] bytes, OpenOption... options) throws IOException {
        Files.createDirectories(path.getParent());
        Files.write(path, bytes, options);
    }

    /**
     * 获取拓展名 (不包括 . ) 例 : "cat.png" 会获得 "png"
     *
     * @param file a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String getFileExtension(String file) {
        var fileName = new File(file).getName();
        var dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }

    /**
     * 获取文件名 (不包括拓展名 ) 例 : "cat.png" 会获得 "cat"
     *
     * @param file a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     */
    public static String getNameWithoutExtension(String file) {
        var fileName = new File(file).getName();
        var dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);
    }

    /**
     * 获取 文件 head
     *
     * @param filePath f
     * @param length   a int
     * @return r
     * @throws java.io.IOException if any.
     */
    public static String getHead(String filePath, int length) throws IOException {
        try (var accessFile = new RandomAccessFile(filePath, "r")) {
            var headBytes = new byte[length];
            accessFile.read(headBytes);
            return HexUtils.toHex(headBytes);
        }
    }

    /**
     * a
     */
    public enum DeleteOption {

        /**
         * 实现清空文件夹的效果
         * 排除根目录 (删除文件为 "文件" 时无效, "目录" 时有效)
         * 比如 未使用此选项调用 delete("/user/test") 文件夹 则 test 文件夹会被删除
         * 若使用此选项则 会清空 test 下所有文件 test 目录则会保留
         */
        EXCLUDE_ROOT;

        static class Info {

            boolean excludeRoot;

            Info(DeleteOption... options) {
                for (var option : options) {
                    switch (option) {
                        case EXCLUDE_ROOT -> this.excludeRoot = true;
                    }
                }
            }

            boolean excludeRoot() {
                return excludeRoot;
            }

        }

    }

}
