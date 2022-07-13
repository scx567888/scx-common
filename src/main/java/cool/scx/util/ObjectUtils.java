package cool.scx.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 处理对象的工具类<br>
 * 本质上就是对 {@link com.fasterxml.jackson.databind.ObjectMapper} 进行了一些简单的封装
 * 注意其中所有方法使用的 ObjectMapper 均采用 {@link JacksonHelper#setIgnoreJsonIgnore} 进行了处理
 * 故此方法中所有方法均忽略 @JsonIgnore 注解
 *
 * @author scx567888
 * @version 0.0.1
 */
public final class ObjectUtils {

    /**
     * 因为 java 无法方便的存储泛型 使用 TypeReference 创建一些常用的类型
     * 此类为 Map 类型
     */
    public static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {
    };

    /**
     * a
     */
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * 忽略 @JsonIgnore 注解的 jsonMapper 一般用于内部使用
     */
    private static final JsonMapper JSON_MAPPER = JacksonHelper.setIgnoreJsonIgnore(JacksonHelper.initJsonMapper());

    /**
     * 忽略 @JsonIgnore 注解的 xmlMapper 一般用于内部使用
     */
    private static final XmlMapper XML_MAPPER = JacksonHelper.setIgnoreJsonIgnore(JacksonHelper.initXmlMapper());

    /**
     * 获取 jsonMapper
     *
     * @return a
     */
    public static JsonMapper jsonMapper() {
        return JSON_MAPPER;
    }

    /**
     * 获取 xmlMapper
     *
     * @return a
     */
    public static XmlMapper xmlMapper() {
        return XML_MAPPER;
    }

    /**
     * a
     *
     * @param type a
     * @return a
     */
    public static JavaType constructType(Type type) {
        return JSON_MAPPER.getTypeFactory().constructType(type);
    }

    /**
     * a
     *
     * @param typeRef a
     * @return a
     */
    public static JavaType constructType(TypeReference<?> typeRef) {
        return constructType(typeRef.getType());
    }

    /**
     * a
     *
     * @param fromValue a
     * @param javaType  a
     * @param <T>       a
     * @return a
     */
    public static <T> T convertValue(Object fromValue, JavaType javaType) {
        return JSON_MAPPER.convertValue(fromValue, javaType);
    }

    /**
     * a
     *
     * @param fromValue a
     * @param tClass    a
     * @param <T>       a
     * @return a
     */
    public static <T> T convertValue(Object fromValue, Class<T> tClass) {
        return convertValue(fromValue, constructType(tClass));
    }

    /**
     * a
     *
     * @param fromValue   a
     * @param toValueType a
     * @param <T>         a
     * @return a
     */
    public static <T> T convertValue(Object fromValue, Type toValueType) {
        return convertValue(fromValue, constructType(toValueType));
    }

    /**
     * a
     *
     * @param fromValue      a
     * @param toValueTypeRef a
     * @param <T>            a
     * @return a
     */
    public static <T> T convertValue(Object fromValue, TypeReference<T> toValueTypeRef) {
        return convertValue(fromValue, constructType(toValueTypeRef));
    }

    /**
     * 将对象转 json 底层调用 JSON_MAPPER.writeValueAsString()
     * 所以会忽略 JsonIgnore 注解 同时如果转换失败则在其内部消化异常 (打印) 并返回 ""
     *
     * @param value        a {@link Object} object.
     * @param defaultValue a {@link Object} object.
     * @return a {@link String} object.
     */
    public static String toJson(Object value, String defaultValue) {
        try {
            return toJson(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * 将对象转 xml 底层调用 XML_MAPPER.writeValueAsString()
     * 所以会忽略 JsonIgnore 注解 同时如果转换失败则在其内部消化异常 (打印) 并返回 ""
     *
     * @param value        a {@link Object} object.
     * @param defaultValue a {@link Object} object.
     * @return a {@link String} object.
     */
    public static String toXml(Object value, String defaultValue) {
        try {
            return toXml(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }

    /**
     * a
     *
     * @param value a
     * @return a
     * @throws JsonProcessingException a
     */
    public static String toJson(Object value) throws JsonProcessingException {
        return JSON_MAPPER.writeValueAsString(value);
    }

    /**
     * a
     *
     * @param value a
     * @return a
     * @throws JsonProcessingException a
     */
    public static String toXml(Object value) throws JsonProcessingException {
        return XML_MAPPER.writeValueAsString(value);
    }

    /**
     * a
     *
     * @param source a
     * @return a
     */
    public static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return EMPTY_OBJECT_ARRAY;
        }
        if (source instanceof Collection) {
            return ((Collection<?>) source).toArray();
        }
        if (source.getClass().isArray()) {
            var length = Array.getLength(source);
            var arr = new Object[length];
            if (source instanceof byte[] arrSource) {
                for (int i = 0; i < length; i = i + 1) {
                    arr[i] = arrSource[i];
                }
            } else if (source instanceof short[] arrSource) {
                for (int i = 0; i < length; i = i + 1) {
                    arr[i] = arrSource[i];
                }
            } else if (source instanceof int[] arrSource) {
                for (int i = 0; i < length; i = i + 1) {
                    arr[i] = arrSource[i];
                }
            } else if (source instanceof long[] arrSource) {
                for (int i = 0; i < length; i = i + 1) {
                    arr[i] = arrSource[i];
                }
            } else if (source instanceof float[] arrSource) {
                for (int i = 0; i < length; i = i + 1) {
                    arr[i] = arrSource[i];
                }
            } else if (source instanceof double[] arrSource) {
                for (int i = 0; i < length; i = i + 1) {
                    arr[i] = arrSource[i];
                }
            } else if (source instanceof boolean[] arrSource) {
                for (int i = 0; i < length; i = i + 1) {
                    arr[i] = arrSource[i];
                }
            } else if (source instanceof char[] arrSource) {
                for (int i = 0; i < length; i = i + 1) {
                    arr[i] = arrSource[i];
                }
            }
            return arr;
        }
        throw new IllegalArgumentException("源数据无法转换为数组对象 !!!");
    }

    /**
     * 将嵌套的 map 扁平化
     *
     * @param sourceMap 源 map
     * @param parentKey a {@link String} object.
     * @return 扁平化后的 map
     */
    private static Map<String, Object> flatMap0(Map<?, ?> sourceMap, String parentKey) {
        var result = new LinkedHashMap<String, Object>();
        var prefix = StringUtils.isBlank(parentKey) ? "" : parentKey + ".";
        sourceMap.forEach((key, value) -> {
            var newKey = prefix + key;
            if (value instanceof Map<?, ?> m) {
                result.putAll(flatMap0(m, newKey));
            } else {
                result.put(newKey, value);
            }
        });
        return result;
    }

    /**
     * 将嵌套的 map 扁平化
     *
     * @param sourceMap 源 map
     * @return 扁平化后的 map
     */
    public static Map<String, Object> flatMap(Map<?, ?> sourceMap) {
        return flatMap0(sourceMap, null);
    }

}
