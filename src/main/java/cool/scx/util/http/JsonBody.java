package cool.scx.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import cool.scx.util.ObjectUtils;

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import static io.netty5.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty5.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;

/**
 * a
 */
public final class JsonBody implements Body {

    private final String bodyStr;

    /**
     * a
     *
     * @param o a
     * @throws JsonProcessingException a
     */
    public JsonBody(Object o) throws JsonProcessingException {
        bodyStr = ObjectUtils.toJson(o);
    }

    @Override
    public HttpRequest.BodyPublisher getBodyPublisher(HttpRequest.Builder builder) {
        builder.setHeader(CONTENT_TYPE.toString(), APPLICATION_JSON + "; charset=utf-8");
        return HttpRequest.BodyPublishers.ofByteArray(bodyStr.getBytes(StandardCharsets.UTF_8));
    }

}
