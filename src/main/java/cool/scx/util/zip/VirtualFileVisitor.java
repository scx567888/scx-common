package cool.scx.util.zip;

/**
 * <p>VirtualFileVisitor interface.</p>
 *
 * @author scx567888
 * @version 0.0.1
 */
public interface VirtualFileVisitor {

    /**
     * 访问文件夹时
     *
     * @param path a
     * @throws java.lang.Exception a
     */
    void visitDirectory(String path) throws Exception;

    /**
     * 访问文件时
     *
     * @param path    a
     * @param content a
     * @throws java.lang.Exception a
     */
    void visitFile(String path, byte[] content) throws Exception;

}
