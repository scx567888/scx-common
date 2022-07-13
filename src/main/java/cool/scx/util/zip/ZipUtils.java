package cool.scx.util.zip;

import cool.scx.util.FileUtils;
import cool.scx.util.StringUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip 压缩器
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class ZipUtils {

    /**
     * 将文件夹压缩成压缩文件
     *
     * @param sourcePath 源文件路径
     * @param outputPath zip 输出路径
     * @param zipOptions a
     * @throws java.io.IOException io
     */
    public static void zip(Path sourcePath, Path outputPath, ZipOption... zipOptions) throws IOException {
        // 获取源路径 path 用于和后续文件进行相对路径的计算
        // 创建一个新的空的输出文件的临时文件
        Files.createDirectories(outputPath.getParent());
        var info = new ZipOption.Info(zipOptions);
        var rootPath = info.includeRoot() ? sourcePath.getFileName().toString() + "/" : "";
        try (var zos = new ZipOutputStream(Files.newOutputStream(outputPath))) {
            //遍历目录
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    var zipEntryPath = sourcePath.relativize(dir).toString();
                    if (StringUtils.notBlank(zipEntryPath)) {
                        if (attrs.isDirectory()) {
                            var zipEntry = new ZipEntry(rootPath + zipEntryPath + "/");
                            zos.putNextEntry(zipEntry);
                            zos.closeEntry();
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    var zipEntryPath = sourcePath.relativize(file).toString();
                    if (StringUtils.notBlank(zipEntryPath)) {
                        ZipEntry zipEntry;
                        if (attrs.isDirectory()) {
                            zipEntry = new ZipEntry(rootPath + zipEntryPath + "/");
                            zos.putNextEntry(zipEntry);
                        } else {
                            zipEntry = new ZipEntry(rootPath + zipEntryPath);
                            zos.putNextEntry(zipEntry);
                            Files.copy(file, zos);
                        }
                        zos.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        }
    }

    /**
     * 解压
     *
     * @param zipFilePath zip文件
     * @param outputPath  解压到的目录
     * @param zipOptions  a
     * @throws java.io.IOException a
     */
    public static void unzip(Path zipFilePath, Path outputPath, ZipOption... zipOptions) throws IOException {
        Files.createDirectories(outputPath);
        var info = new ZipOption.Info(zipOptions);
        var rootPath = info.includeRoot() ? FileUtils.getNameWithoutExtension(zipFilePath.getFileName().toString()) + "/" : "";
        try (var zis = new ZipInputStream(Files.newInputStream(zipFilePath))) {
            // 遍历每一个文件
            var zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                var unzipFilePath = outputPath.resolve(rootPath + zipEntry.getName());
                if (zipEntry.isDirectory()) { // 文件夹
                    Files.createDirectories(unzipFilePath);
                } else { // 文件
                    Files.createDirectories(unzipFilePath.getParent());
                    Files.copy(zis, unzipFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
        }
    }

    /**
     * a
     */
    public enum ZipOption {

        /**
         * 是否包含根目录
         * 压缩时使用则会直接压缩整个文件夹, 否则相当于压缩文件内文件夹
         * 解压时使用则会在待解压目录创建与压缩包名称相同的文件夹
         */
        INCLUDE_ROOT;

        static class Info {

            boolean includeRoot;

            Info(ZipOption... options) {
                for (var option : options) {
                    switch (option) {
                        case INCLUDE_ROOT -> this.includeRoot = true;
                    }
                }
            }

            boolean includeRoot() {
                return includeRoot;
            }

        }

    }

}
