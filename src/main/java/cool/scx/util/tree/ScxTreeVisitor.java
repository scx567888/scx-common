package cool.scx.util.tree;

import java.util.List;

/**
 * <p>ScxTreeVisitor interface.</p>
 *
 * @author scx567888
 * @version 0.0.1
 */
public interface ScxTreeVisitor<T extends ScxTree<T>> {

    /**
     * <p>handle.</p>
     *
     * @param parents        a {@link List} object
     * @param currentScxTree a T object
     * @throws Exception if any.
     */
    void handle(List<T> parents, T currentScxTree) throws Exception;

}
