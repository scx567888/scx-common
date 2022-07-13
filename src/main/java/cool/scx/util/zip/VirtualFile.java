package cool.scx.util.zip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 虚拟文件
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class VirtualFile extends AbstractVirtualFile {

    /**
     * 物理文件
     */
    private final File physicalFile;

    /**
     * 字节数组
     */
    private final byte[] bytes;

    /**
     * 虚拟文件类型
     * 0 为源自文件 ,1 为源自字节数组
     */
    private final int type;

    /**
     * <p>Constructor for VirtualFile.</p>
     *
     * @param physicalFile a
     * @param bytes        a
     * @param fileName     a
     * @param type         a
     */
    private VirtualFile(File physicalFile, byte[] bytes, String fileName, int type) {
        super(fileName, null);
        this.physicalFile = physicalFile;
        this.bytes = bytes;
        this.type = type;
    }

    /**
     * 通过 文件(File) 创建
     *
     * @param physicalFile a
     * @return a
     */
    public static VirtualFile of(File physicalFile) {
        return new VirtualFile(physicalFile, null, physicalFile.getName(), 0);
    }

    /**
     * 通过 文件() 创建
     *
     * @param name         a
     * @param physicalFile a
     * @return a
     */
    public static VirtualFile of(String name, File physicalFile) {
        return new VirtualFile(physicalFile, null, name, 0);
    }

    /**
     * 通过 byte 创建
     *
     * @param name  a
     * @param bytes a
     * @return a
     */
    public static VirtualFile of(String name, byte[] bytes) {
        return new VirtualFile(null, bytes, name, 1);
    }

    /**
     * 获取虚拟文件的字节
     *
     * @return a
     * @throws IOException a
     */
    public byte[] getBytes() throws IOException {
        if (type == 0) {
            return Files.readAllBytes(this.physicalFile.toPath());
        } else {
            return bytes;
        }
    }

}
