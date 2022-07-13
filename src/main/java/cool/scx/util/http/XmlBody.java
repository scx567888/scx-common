package cool.scx.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import cool.scx.util.ObjectUtils;

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

import static io.netty5.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty5.handler.codec.http.HttpHeaderValues.APPLICATION_XML;

/**
 * a
 */
public final class XmlBody implements Body {

    private final String bodyStr;

    /**
     * a
     *
     * @param o a
     * @throws JsonProcessingException a
     */
    public XmlBody(Object o) throws JsonProcessingException {
        bodyStr = ObjectUtils.toXml(o);
    }

    @Override
    public HttpRequest.BodyPublisher getBodyPublisher(HttpRequest.Builder builder) {
        builder.setHeader(CONTENT_TYPE.toString(), APPLICATION_XML + "; charset=utf-8");
        return HttpRequest.BodyPublishers.ofByteArray(bodyStr.getBytes(StandardCharsets.UTF_8));
    }

}
