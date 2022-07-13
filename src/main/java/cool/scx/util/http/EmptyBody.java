package cool.scx.util.http;

import java.net.http.HttpRequest;

/**
 * a
 *
 * @author scx567888
 * @version 0.0.1
 */
final class EmptyBody implements Body {

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpRequest.BodyPublisher getBodyPublisher(HttpRequest.Builder builder) {
        return HttpRequest.BodyPublishers.noBody();
    }

}
