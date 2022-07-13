package cool.scx.util.http;

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import static io.netty5.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty5.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;

/**
 * a
 */
public final class StringBody implements Body {

    private final String bodyStr;

    /**
     * a
     *
     * @param str a
     */
    public StringBody(String str) {
        bodyStr = str;
    }

    @Override
    public HttpRequest.BodyPublisher getBodyPublisher(HttpRequest.Builder builder) {
        builder.setHeader(CONTENT_TYPE.toString(), TEXT_PLAIN + "; charset=utf-8");
        return HttpRequest.BodyPublishers.ofByteArray(bodyStr.getBytes(StandardCharsets.UTF_8));
    }

}
