package cool.scx.util.http;

import cool.scx.util.RandomUtils;
import cool.scx.util.exception.ScxExceptionHelper;
import io.netty5.buffer.BufferUtil;
import io.netty5.buffer.api.BufferAllocator;
import io.vertx.core.http.impl.MimeMapping;

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * a
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class FormData implements Body {

    /**
     * a
     */
    private static final String FORM_BOUNDARY_PREFIX = "----ScxHttpClientHelperFormBoundary";

    /**
     * Constant <code>lineSeparator="\r\n"</code>
     */
    private static final String lineSeparator = "\r\n";

    /**
     * Constant <code>lineSeparatorByteArray</code>
     */
    private static final byte[] lineSeparatorBytes = lineSeparator.getBytes(StandardCharsets.UTF_8);

    private final List<FormDataItem> formDataItemList = new ArrayList<>();

    private static byte[] getStart(FormDataItem f, String boundary) {
        var headerStr = f.filename == null ?
                "Content-Disposition: form-data; name=\"" + f.name + "\"" :
                "Content-Disposition: form-data; name=\"" + f.name + "\"; filename=\"" + f.filename + "\"";
        var finalHeader = f.contentType == null ?
                headerStr :
                headerStr + lineSeparator + "Content-Type: " + f.contentType;
        var start = "--" + boundary + lineSeparator + finalHeader + lineSeparator + lineSeparator;
        return start.getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] getContent(FormDataItem nowItem) {
        return switch (nowItem.type) {
            case CONTENT -> nowItem.content;
            case FILE -> ScxExceptionHelper.wrap(() -> Files.readAllBytes(nowItem.filePath));
        };
    }

    private static byte[] getEnd(String boundary) {
        var end = "--" + boundary + "--";
        return end.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * a
     *
     * @param name a
     * @param text a
     * @return a
     */
    public FormData add(String name, Object text) {
        this.formDataItemList.add(new FormDataItem(name, text.toString()));
        return this;
    }

    /**
     * a
     *
     * @param name     a
     * @param filePath a
     * @return a
     */
    public FormData addFile(String name, Path filePath) {
        this.formDataItemList.add(new FormDataItem(name, filePath));
        return this;
    }

    /**
     * a
     *
     * @param name        a
     * @param fileByte    a
     * @param filename    a
     * @param contentType a
     * @return a
     */
    public FormData addFile(String name, byte[] fileByte, String filename, String contentType) {
        this.formDataItemList.add(new FormDataItem(name, fileByte, filename, contentType));
        return this;
    }

    /**
     * a
     *
     * @param name     a
     * @param fileByte a
     * @param filename a
     * @return a
     */
    public FormData addFile(String name, byte[] fileByte, String filename) {
        this.formDataItemList.add(new FormDataItem(name, fileByte, filename));
        return this;
    }

    /**
     * a
     *
     * @param name a
     * @return a
     */
    public FormData remove(String name) {
        this.formDataItemList.removeIf(formDataItem -> name.equals(formDataItem.name));
        return this;
    }

    /**
     * a
     *
     * @param builder a a
     * @return a
     */
    @Override
    public HttpRequest.BodyPublisher getBodyPublisher(HttpRequest.Builder builder) {
        final String boundary = FORM_BOUNDARY_PREFIX + RandomUtils.randomString(8);
        builder.setHeader("content-cool.scx.type", "multipart/form-data; boundary=" + boundary);
        try (var allocator = BufferAllocator.onHeapUnpooled(); var buffer = allocator.compose()) {
            for (var formDataItem : formDataItemList) {
                buffer.writeBytes(getStart(formDataItem, boundary));
                buffer.writeBytes(getContent(formDataItem));
                buffer.writeBytes(lineSeparatorBytes);
            }
            buffer.writeBytes(getEnd(boundary));
            return HttpRequest.BodyPublishers.ofByteArray(BufferUtil.getBytes(buffer));
        }

    }

    enum FormDataItemType {
        CONTENT, FILE
    }

    static final class FormDataItem {
        final FormDataItemType type;
        final String name;
        final String filename;
        final String contentType;
        Path filePath;
        byte[] content;

        FormDataItem(String name, Object text) {
            this(name, text.toString().getBytes(StandardCharsets.UTF_8), null, null);
        }

        FormDataItem(String name, byte[] content, String filename) {
            this(name, content, filename, MimeMapping.getMimeTypeForFilename(filename));
        }

        FormDataItem(String name, byte[] content, String filename, String contentType) {
            this.type = FormDataItemType.CONTENT;
            this.name = name;
            this.content = content;
            this.filename = filename;
            this.contentType = contentType;
        }

        FormDataItem(String name, Path filePath) {
            this.type = FormDataItemType.FILE;
            this.name = name;
            this.filePath = filePath;
            this.filename = this.filePath.getFileName().toString();
            this.contentType = MimeMapping.getMimeTypeForFilename(this.filename);
        }

    }

}
