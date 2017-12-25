package ldh.fx.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by ldh on 2017/2/26.
 */
public class FileUtil {

    public static String loadJarFile(String file) throws Exception {
        InputStream inputStream = FileUtil.class.getResourceAsStream(file);
        String data = loadFile(inputStream);
        inputStream.close();
        return data;
    }

    public static String loadFile(InputStream inputStream) throws Exception {
        int size = inputStream.available();
        byte[] data = new byte[size];
        int l = inputStream.read(data);
        return new String(data, "utf-8");
    }

    public static String getSourceRoot() {
        return System.getProperty("user.dir") + "/";
    }

    public static String loadFile(String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        for(String line : lines){
            sb.append(line).append("\r\n");
        }
        return sb.toString();
    }

    public static void saveFile(String file, String json) throws IOException {
        Files.write(Paths.get(file), json.getBytes("utf-8"), StandardOpenOption.CREATE);
    }

    public static void main(String[] args) {
        System.out.println(getSourceRoot());
    }
}
