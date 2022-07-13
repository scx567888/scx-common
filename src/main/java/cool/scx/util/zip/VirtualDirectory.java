package cool.scx.util.zip;

import cool.scx.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>VirtualDirectory class.</p>
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class VirtualDirectory extends AbstractVirtualFile {

    /**
     * 内部构造函数
     *
     * @param directoryName a
     */
    private VirtualDirectory(String directoryName) {
        super(directoryName, new ArrayList<>());
    }

    /**
     * 如果不设置文件夹名称则不会创建文件夹 其下的文件会直接 放置到和当前 目录相同的目录
     *
     * @return a
     */
    public static VirtualDirectory of() {
        return new VirtualDirectory(null);
    }

    /**
     * 设置文件夹
     *
     * @param directoryName a
     * @return a
     */
    public static VirtualDirectory of(String directoryName) {
        return new VirtualDirectory(directoryName);
    }

    /**
     * 获取有效的路径
     *
     * @param paths p
     * @return a
     */
    private static String[] getValidPaths(String paths) {
        if (StringUtils.notBlank(paths)) {
            return Arrays.stream(paths.split("/")).filter(StringUtils::notBlank).toArray(String[]::new);
        } else {
            throw new IllegalArgumentException("Path 不能为空");
        }
    }

    /**
     * 通过路径 向文件中添加 如果路径不存在或者路径是一个文件则抛出异常
     *
     * @param pathStr     p
     * @param virtualFile v
     * @return 返回自身以便链式调用
     */
    public VirtualDirectory put(String pathStr, AbstractVirtualFile virtualFile) {
        var childrenByPath = getOrCreate(pathStr);
        if (childrenByPath instanceof VirtualDirectory) {
            ((VirtualDirectory) childrenByPath).put(virtualFile);
        } else {
            throw new IllegalArgumentException("路径为 [" + pathStr + "] 的虚拟文件不是目录, 无法添加子文件或子目录 !!!");
        }
        return this;
    }

    /**
     * 像当前目录中添加一个虚拟文件或虚拟目录
     *
     * @param virtualFile v
     * @return v
     */
    public VirtualDirectory put(AbstractVirtualFile virtualFile) {
        for (var child : this.children) {
            if (child.name != null && child.name.equalsIgnoreCase(virtualFile.name)) {
                throw new IllegalArgumentException("不能重复添加相同名称的 IVirtualFile : " + virtualFile.name);
            }
        }
        virtualFile.setParent(this);
        children.add(virtualFile);
        return this;
    }

    /**
     * 根据路径获取一个文件 未找到会 抛出异常
     *
     * @param pathStr p
     * @return s
     */
    public AbstractVirtualFile get(String pathStr) {
        var paths = getValidPaths(pathStr);
        AbstractVirtualFile findByPath = this;
        for (var path : paths) {
            if (findByPath != null) {
                findByPath = findByPath instanceof VirtualDirectory ?
                        ((VirtualDirectory) findByPath).findChildren(path) :
                        null;
            } else {
                break;
            }
        }
        if (findByPath == null) {
            throw new IllegalArgumentException("未找到路径为 [" + pathStr + "] 的虚拟文件 !!!");
        }
        return findByPath;
    }

    /**
     * 获取文件或文件夹 如果不存在则根据路径创建相对应的目录
     *
     * @param pathStr path (多层级目录请以 "/" 分开)
     * @return 获取到的文件
     */
    public AbstractVirtualFile getOrCreate(String pathStr) {
        //先把路径 切割为待创建的路径数组
        var paths = getValidPaths(pathStr);
        //假设当前 虚拟目录 为当前对象
        AbstractVirtualFile findByPath = this;
        //标识到了第几层
        int deep = 1;
        for (var path : paths) {
            //先根据路径获取 子节点
            var tempChildren = findByPath instanceof VirtualDirectory ?
                    ((VirtualDirectory) findByPath).findChildren(path) :
                    null;
            //当前路径深度为 1 或者 是最后一层了 或者为空 或者 是一个文件夹
            if (paths.length == 1 || deep == paths.length || tempChildren == null || tempChildren instanceof VirtualDirectory) {
                //如果当前目录是一个文件夹 并且 里面的 children 也没有拿到 就说明可以创建一个
                if (tempChildren == null && findByPath instanceof VirtualDirectory) {
                    var temp = VirtualDirectory.of(path);
                    ((VirtualDirectory) findByPath).put(temp);
                    findByPath = temp;
                } else {
                    findByPath = tempChildren;
                }
            } else {
                throw new IllegalArgumentException("路径为 [" + String.join("/", Arrays.copyOfRange(paths, 0, deep)) + "] 已存在文件, 无法添加子文件或子目录 !!!");
            }
            deep = deep + 1;
        }
        return findByPath;
    }

    /**
     * {@inheritDoc}
     *
     * @param name a {@link java.lang.String} object
     * @return a {@link cool.scx.util.zip.AbstractVirtualFile} object
     */
    public AbstractVirtualFile findChildren(String name) {
        for (var child : children) {
            if (child.name != null && child.name.equalsIgnoreCase(name)) {
                return child;
            }
        }
        return null;
    }

}
