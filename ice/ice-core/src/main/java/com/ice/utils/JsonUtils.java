package com.ice.utils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * JSON的工具类
 * <p/>
 * <h3>Here is an example:</h3>
 * <p/>
 * <pre>
 *     // 将json通过类型转换成对象
 *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
 * </pre>
 * <hr />
 * <pre>
 *     // 传入转换的引用类型
 *     {@link JsonUtil JsonUtil}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
 * </pre>
 * <hr />
 * <pre>
 *     // 将对象转换成json
 *     {@link JsonUtil JsonUtil}.toJson(user);
 * </pre>
 * <hr />
 * <pre>
 *     // 将对象转换成json, 可以设置输出属性
 *     {@link JsonUtil JsonUtil}.toJson(user, {@link Inclusion Inclusion.ALWAYS});
 * </pre>
 * <hr />
 * <pre>
 *     // 将对象转换成json, 传入配置对象
 *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
 *     mapper.setSerializationInclusion({@link Inclusion Inclusion.ALWAYS});
 *     mapper.configure({@link Feature Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
 *     mapper.configure({@link Feature Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
 *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd HH:mm:ss"));
 *     {@link JsonUtil JsonUtil}.toJson(user, mapper);
 * </pre>
 * <hr />
 * <pre>
 *     // 获取Mapper对象
 *     {@link JsonUtil JsonUtil}.mapper();
 * </pre>
 *
 * @see JsonUtil JsonUtil
 * @see Feature Feature
 * @see ObjectMapper ObjectMapper
 * @see Inclusion Inclusion
 * @see IOException IOException
 * @see SimpleDateFormat SimpleDateFormat
 */
@SuppressWarnings("unchecked")
public final class JsonUtils {

	private static ObjectMapper MAPPER;

	static {
		MAPPER = generateMapper(Inclusion.NON_NULL);
	}


	private JsonUtils() {
	}

	/**
	 * 将json通过类型转换成对象
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
	 * </pre>
	 *
	 * @param json  json字符串
	 * @param clazz 泛型类型
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return clazz.equals(String.class) ? (T) json : mapper().readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json通过类型转换成对象
	 * <p/>
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
	 * </pre>
	 *
	 * @param json          json字符串
	 * @param typeReference 引用类型
	 * @return 返回对象
	 * @throws IOException
	 */
	public static <T> T fromJson(String json, TypeReference<?> typeReference) {
		try {
			return (T) (String.class.equals(typeReference.getType()) ? json : mapper().readValue(json, typeReference));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将对象转换成json
	 * <p/>
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.toJson(user);
	 * </pre>
	 *
	 * @param src 对象
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T src) {
		try {
			return src instanceof String ? (String) src : mapper().writeValueAsString(src);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将对象转换成json, 可以设置输出属性
	 * <p/>
	 * <pre>
	 *     {@link JsonUtil JsonUtil}.toJson(user, {@link Inclusion Inclusion.ALWAYS});
	 * </pre>
	 * <p/>
	 * {@link Inclusion Inclusion 对象枚举}
	 * <ul>
	 * <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
	 * </ul>
	 *
	 * @param src       对象
	 * @param inclusion 传入一个枚举值, 设置输出属性
	 * @return 返回json字符串
	 * @throws IOException
	 */
	public static <T> String toJson(T src, Inclusion inclusion) throws IOException {
		if (src instanceof String) {
			return (String) src;
		} else {
			return mapper().writeValueAsString(src);
		}
	}

	/**
	 * 将对象转换成json, 传入配置对象
	 * <p/>
	 * <pre>
	 *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
	 *     mapper.setSerializationInclusion({@link Inclusion Inclusion.ALWAYS});
	 *     mapper.configure({@link Feature Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
	 *     mapper.configure({@link Feature Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
	 *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd HH:mm:ss"));
	 *     {@link JsonUtil JsonUtil}.toJson(user, mapper);
	 * </pre>
	 * <p/>
	 * {@link ObjectMapper ObjectMapper}
	 *
	 * @param src    对象
	 * @param mapper 配置对象
	 * @return 返回json字符串
	 * @throws IOException
	 * @see ObjectMapper
	 */
	public static <T> String toJson(T src, ObjectMapper mapper) throws IOException {
		if (null != mapper) {
			if (src instanceof String) {
				return (String) src;
			} else {
				return mapper().writeValueAsString(src);
			}
		} else {
			return null;
		}
	}


	/**
	 * 将对象转换成map, 传入配置对象
	 * <p/>
	 * <pre>
	 *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
	 *     mapper.setSerializationInclusion({@link Inclusion Inclusion.ALWAYS});
	 *     mapper.configure({@link Feature Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
	 *     mapper.configure({@link Feature Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
	 *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd HH:mm:ss"));
	 *     {@link JsonUtil JsonUtil}.toJson(user, mapper);
	 * </pre>
	 * <p/>
	 * {@link ObjectMapper ObjectMapper}
	 *
	 * @param <T>
	 * @param src    对象
	 * @param mapper 配置对象
	 * @return 返回map
	 * @throws IOException
	 * @see ObjectMapper
	 */
	public static Map<String, Object> toMap(String src) throws IOException {
		Map<String, Object> map = mapper().readValue(src, Map.class);
		return map;
	}


	/**
	 * 返回{@link ObjectMapper ObjectMapper}对象, 用于定制性的操作
	 *
	 * @return {@link ObjectMapper ObjectMapper}对象
	 */
	public static ObjectMapper mapper() {
		return MAPPER;
	}

	/**
	 * 通过Inclusion创建ObjectMapper对象
	 * <p/>
	 * {@link Inclusion Inclusion 对象枚举}
	 * <ul>
	 * <li>{@link Inclusion Inclusion.ALWAYS 全部列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
	 * <li>{@link Inclusion Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
	 * </ul>
	 *
	 * @param inclusion 传入一个枚举值, 设置输出属性
	 * @return 返回ObjectMapper对象
	 */
	private static ObjectMapper generateMapper(Inclusion inclusion) {
		ObjectMapper customMapper=new ObjectMapper();

		// 设置输出时包含属性的风格
		customMapper.setSerializationInclusion(inclusion);

		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		customMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		// 禁止使用int代表Enum的order()來反序列化Enum,非常危險
		customMapper.configure(Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
		// 允许属性名无引号
		customMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

		// 允许属性值使用单引号
		customMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		// 所有日期格式都统一为以下样式
		customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return customMapper;
	}


}
