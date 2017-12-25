package ldh.fx.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ldh.common.PageResult;
import ldh.common.json.JsonView;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by ldh on 2017/4/18.
 */
public class JsonHttpUtil {

    public static <T>T get(JsonView jsonView, String url, Class<T> clazz) {
        String json = HttpClientFactory.getInstance().get(url);
        return ParseJson(jsonView, clazz, json);
    }

    public static <T>T get(JsonView jsonView, String url, Type type) {
        String json = HttpClientFactory.getInstance().get(url);
        return ParseJson(jsonView, type, json);
    }

    private static <T> T ParseJson(JsonView jsonView, Type type, String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        ;
        boolean isSuccess = jsonObject.get("isSuccess").getAsBoolean();
        if (isSuccess) {
            JsonElement jsonElement = jsonObject.get("data");
            if (type.getTypeName().equals(Void.class.getName())) {
                return null;
            }
            return jsonView.fromJson(jsonElement.toString(), type);
        }
        throw new RuntimeException("获取数据失败, data:" + json);
    }

    public static <T>T get(JsonView jsonView, String url, Map<String, Object> paramMap, Class<T> clazz) {
        String json = HttpClientFactory.getInstance().get(buildUrl(url, paramMap));
        return ParseJson(jsonView, clazz, json);
    }

    public static <T>PageResult<T> get(JsonView jsonView, String url, Map<String, Object> paramMap, Type type) {
        String json = HttpClientFactory.getInstance().get(buildUrl(url, paramMap));
        return ParseJson(jsonView, type, json);
    }

    public static <T>T post(JsonView jsonView, String url, Map<String, Object> paramMap, Class<T> clazz) {
        String json = HttpClientFactory.getInstance().post(url, paramMap);
        return ParseJson(jsonView, clazz, json);
    }

    public static <T>PageResult<T> post(JsonView jsonView, String url, Map<String, Object> paramMap, Type clazz) {
        String json = HttpClientFactory.getInstance().post(url, paramMap);
        return ParseJson(jsonView, clazz, json);
    }

    private static String buildUrl(String url, Map<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
        }
        String u = url + "?" + sb.toString();
        System.out.println("url:" + u);
        return u;
    }
}
