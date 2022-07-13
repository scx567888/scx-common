package cool.scx.util.http;

import java.net.http.HttpRequest;

/**
 * a
 *
 * @author scx567888
 * @version 0.0.1
 */
public interface Body {

    /**
     * a
     *
     * @param builder a
     * @return a
     */
    HttpRequest.BodyPublisher getBodyPublisher(HttpRequest.Builder builder);

}
