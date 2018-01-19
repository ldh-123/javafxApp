package ldh.descktop.util;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class CvsPropertiesUtil {

    private static Properties properties = new Properties();

    static {
        InputStream input = null;
        try {
            input = new FileInputStream(getFilePath());
            properties.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getFilePath() {
        String dir = System.getProperty("user.dir");
        return dir + "/config.properties";
    }

    public static void save() {
        OutputStream output = null;
        try {
            output = new FileOutputStream(getFilePath());
            properties.store(output, "andieguo modify" + new Date().toString());// 保存键值对到文件中
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void putAndSave(String key, String value) {
        properties.put(key, value);
        save();
    }

    public static void appendAndSave(String key, String value) {
        String tmp= properties.getProperty(key);
        String saveStr = value;
        if (tmp != null) {
            String[] tts = tmp.split(",");
            for (String tt : tts) {
                if (tt.equals(value)) return;
            }
            saveStr = tmp + "," + value;
        }
        putAndSave(key, saveStr);
    }

    public static void removeAndSave(String key, String value) {
        String tmp= properties.getProperty(key);
        String saveStr = "";
        if (tmp != null) {
            String[] tts = tmp.split(",");
            for (String tt : tts) {
                if (!tt.equals(value)) {
                    saveStr = "," + tt;
                }
            }
            saveStr = saveStr.substring(1);
        }
        putAndSave(key, saveStr);
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
