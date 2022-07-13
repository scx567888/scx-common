package cool.scx.util.zip;

import cool.scx.util.tree.ScxTree;
import cool.scx.util.tree.ScxTreeUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>IVirtualFile interface.</p>
 *
 * @author scx567888
 * @version 0.0.1
 */
public abstract class AbstractVirtualFile implements ScxTree<AbstractVirtualFile> {

    /**
     * a
     */
    protected final String name;

    /**
     * a
     */
    protected final List<AbstractVirtualFile> children;

    /**
     * a
     */
    protected AbstractVirtualFile parent;

    /**
     * a
     *
     * @param name     a
     * @param children a
     */
    public AbstractVirtualFile(String name, List<AbstractVirtualFile> children) {
        this.name = name;
        this.children = children;
    }

    /**
     * 将虚拟文件写入到 zip 流
     *
     * @param virtualFile a
     * @param zos         a
     * @throws java.lang.Exception a
     */
    private static void writeVirtualFileToZipOutputStream(AbstractVirtualFile virtualFile, ZipOutputStream zos) throws Exception {
        virtualFile.walk(new VirtualFileVisitor() {
            @Override
            public void visitDirectory(String path) throws IOException {
                var zipEntry = new ZipEntry(path);
                zos.putNextEntry(zipEntry);
                zos.closeEntry();
            }

            @Override
            public void visitFile(String path, byte[] bytes) throws IOException {
                var zipEntry = new ZipEntry(path);
                zos.putNextEntry(zipEntry);
                zos.write(bytes);
                zos.closeEntry();
            }

        });
    }

    /**
     * <p>getFullPath.</p>
     *
     * @param abstractVirtualFiles1 a {@link java.util.List} object
     * @param abstractVirtualFiles2 a {@link cool.scx.util.zip.AbstractVirtualFile} object
     * @return a {@link java.lang.String} object
     */
    private static String getFullPath(List<AbstractVirtualFile> abstractVirtualFiles1, AbstractVirtualFile... abstractVirtualFiles2) {
        var fullList = new ArrayList<AbstractVirtualFile>();
        if (abstractVirtualFiles1 != null) {
            fullList.addAll(abstractVirtualFiles1);
        }
        Collections.addAll(fullList, abstractVirtualFiles2);
        return fullList.stream().map((f) -> f.name).collect(Collectors.joining("/"));
    }

    /**
     * a
     *
     * @param parent a
     */
    public final void setParent(AbstractVirtualFile parent) {
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final AbstractVirtualFile parent() {
        return this.parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<AbstractVirtualFile> children() {
        return children;
    }

    /**
     * 将 virtualFile 转换为 byte 数组 方便前台用户下载使用
     *
     * @return a
     * @throws java.lang.Exception a
     */
    public byte[] toZipBytes() throws Exception {
        var bo = new ByteArrayOutputStream();
        try (var zos = new ZipOutputStream(bo)) {
            //遍历目录
            writeVirtualFileToZipOutputStream(this, zos);
        }
        return bo.toByteArray();
    }

    /**
     * 将一个虚拟文件压缩
     *
     * @param outputPath a
     * @throws java.lang.Exception a
     */
    public void toZipFile(Path outputPath) throws Exception {
        // 创建一个新的空的输出文件的临时文件
        Files.createDirectories(outputPath.getParent());
        try (var zos = new ZipOutputStream(Files.newOutputStream(outputPath))) {
            //遍历目录
            writeVirtualFileToZipOutputStream(this, zos);
        }
    }

    /**
     * 循环遍历一个 虚拟文件
     *
     * @param visitor a
     * @throws java.lang.Exception a
     */
    public void walk(VirtualFileVisitor visitor) throws Exception {
        ScxTreeUtil.walk(this, (parent, self) -> {
            var fullPath = getFullPath(parent, self);
            if (self instanceof VirtualFile) {
                visitor.visitFile(fullPath, ((VirtualFile) self).getBytes());
            } else if (self instanceof VirtualDirectory) {
                // 文件夹需以 / 结尾
                visitor.visitDirectory(fullPath + "/");
            }
        });
    }

}
