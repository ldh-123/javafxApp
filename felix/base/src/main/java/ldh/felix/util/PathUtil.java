package ldh.felix.util;

/**
 * Created by ldh on 2018/2/8.
 */
public class PathUtil {

    public static String getRoot(String projectName) {
        try {
            String path = PathUtil.class.getResource("/").getPath();
            int idx = path.indexOf(projectName);
            return path.substring(0, idx) + projectName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return System.getProperty("user.dir");
    }
}
