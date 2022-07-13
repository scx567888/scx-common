package cool.scx.util.tree;

import java.util.List;

/**
 * 树接口 注意和 {@link cool.scx.util.tree.ScxTreeModel} 进行区分 两者用途不同
 *
 * @param <T> T
 * @author scx567888
 * @version 0.0.1
 */
public interface ScxTree<T extends ScxTree<T>> {

    /**
     * <p>parent.</p>
     *
     * @return a T object
     */
    default T parent() {
        return null;
    }

    /**
     * <p>children.</p>
     *
     * @return a {@link java.util.List} object
     */
    default List<T> children() {
        return null;
    }

}
