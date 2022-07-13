package cool.scx.util.http;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

/**
 * a
 */
public final class Options {

    private final HttpRequest.Builder builder = HttpRequest.newBuilder();

    private HttpClient httpClient;

    /**
     * a
     *
     * @param httpClient a
     * @return a
     */
    public Options httpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    /**
     * a
     *
     * @param name  a
     * @param value a
     * @return a
     */
    public Options header(String name, String value) {
        builder.header(name, value);
        return this;
    }

    /**
     * 超时时间
     *
     * @param duration a
     * @return a
     */
    public Options timeout(Duration duration) {
        builder.timeout(duration);
        return this;
    }

    /**
     * a
     *
     * @param name  a
     * @param value a
     * @return a
     */
    public Options setHeader(String name, String value) {
        builder.setHeader(name, value);
        return this;
    }

    /**
     * a
     *
     * @return a
     */
    public HttpRequest.Builder getHttpRequestBuilder() {
        return builder;
    }

    /**
     * a
     *
     * @return a
     */
    public HttpClient httpClient() {
        return httpClient;
    }

}