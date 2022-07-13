package cool.scx.util.http;

import cool.scx.util.exception.ScxExceptionHelper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * 针对 {@link HttpClient} 进行一些极其简单的封装
 * <p>
 * 如需处理复杂的情况请直接使用  {@link HttpClient}
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class HttpClientHelper {

    /**
     * 默认的 HTTP_CLIENT 实例
     */
    public static final HttpClient DEFAULT_HTTP_CLIENT = HttpClient.newBuilder().build();

    /**
     * 默认的 RESPONSE_BODY_HANDLER
     */
    private static final HttpResponse.BodyHandler<String> DEFAULT_RESPONSE_BODY_HANDLER = HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8);

    /**
     * a
     *
     * @param url a
     * @return a
     */
    public static HttpResponse<String> get(String url) {
        return get(url, null);
    }

    /**
     * a
     *
     * @param url     a
     * @param options a
     * @return a
     */
    public static HttpResponse<String> get(String url, Options options) {
        return request(url, "GET", null, options);
    }

    /**
     * a
     *
     * @param url a
     * @return a
     */
    public static HttpResponse<String> post(String url) {
        return post(url, null);
    }

    /**
     * a
     *
     * @param url  a
     * @param body a
     * @return a
     */
    public static HttpResponse<String> post(String url, Body body) {
        return post(url, body, null);
    }

    /**
     * a
     *
     * @param url     a
     * @param body    a
     * @param options a
     * @return a
     */
    public static HttpResponse<String> post(String url, Body body, Options options) {
        return request(url, "POST", body, options);
    }

    /**
     * a
     *
     * @param url a
     * @return a
     */
    public static HttpResponse<String> delete(String url) {
        return delete(url, null);
    }

    /**
     * a
     *
     * @param url     a
     * @param options a
     * @return a
     */
    public static HttpResponse<String> delete(String url, Options options) {
        return request(url, "DELETE", null, options);
    }

    /**
     * a
     *
     * @param url a
     * @return a
     */
    public static HttpResponse<String> put(String url) {
        return put(url, null);
    }

    /**
     * a
     *
     * @param url  a
     * @param body a
     * @return a
     */
    public static HttpResponse<String> put(String url, Body body) {
        return put(url, body, null);
    }

    /**
     * a
     *
     * @param url     a
     * @param body    a
     * @param options a
     * @return a
     */
    public static HttpResponse<String> put(String url, Body body, Options options) {
        return request(url, "PUT", body, options);
    }

    /**
     * a
     *
     * @param url     a
     * @param method  a
     * @param body    a
     * @param options a
     * @return a
     */
    private static HttpResponse<String> request(String url, String method, Body body, Options options) {
        return ScxExceptionHelper.wrap(() -> {
            var b = body != null ? body : new EmptyBody();
            var o = options != null ? options : new Options();
            var httpClient = o.httpClient() != null ? o.httpClient() : DEFAULT_HTTP_CLIENT;
            var httpRequestBuilder = o.getHttpRequestBuilder().uri(URI.create(url));
            var bodyPublisher = b.getBodyPublisher(httpRequestBuilder);
            var httpRequest = switch (method) {
                case "GET" -> httpRequestBuilder.GET().build();
                case "POST" -> httpRequestBuilder.POST(bodyPublisher).build();
                case "DELETE" -> httpRequestBuilder.DELETE().build();
                case "PUT" -> httpRequestBuilder.PUT(bodyPublisher).build();
                default -> throw new IllegalArgumentException("未知的 method : " + method);
            };
            return httpClient.send(httpRequest, DEFAULT_RESPONSE_BODY_HANDLER);
        });
    }

}
