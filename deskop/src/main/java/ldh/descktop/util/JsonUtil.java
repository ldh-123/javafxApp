package ldh.descktop.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

/**
 * Created by ldh on 2018/4/20.
 */
public class JsonUtil {

    public static String formatter(String content){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(content));
        reader.setLenient(true);
        JsonParser jsonPar = new JsonParser();
        JsonElement jsonEl = jsonPar.parse(reader);
        String prettyJson = gson.toJson(jsonEl);
        System.out.println(prettyJson);
        return prettyJson;

    }
}
