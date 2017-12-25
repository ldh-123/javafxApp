package ldh.fx.util;

import java.lang.reflect.Method;

/**
 * Created by ldh on 2017/4/17.
 */
public class MethodUtil {

    public static Method getMethod(Class<?> clazz, String methodName, Class<?> ... classes) {
        if (clazz == Object.class) {
            return null;
        }
        try{
            return clazz.getDeclaredMethod(methodName, classes);
        } catch (Exception e) {
           return  getMethod(clazz.getSuperclass(), methodName, classes);
        }
    }
}
