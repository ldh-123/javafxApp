package daymac;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;

public class JarLoaderTest {

    public static void main(String[] args) {
        try {
            //第一种  配置成文件格式
            File file = new File("D:\\jarload\\test.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            String s = new String();
            while ((s = in.readLine()) != null) {

                URL url = new URL(s);
                s = null;

                URLClassLoader myClassLoader = new URLClassLoader(new URL[] { url }, Thread.currentThread()
                        .getContextClassLoader());
                Class<? extends CompileTest> myClass = (Class<? extends CompileTest>) myClassLoader.loadClass("com.java.jarloader.TestAction");
                CompileTest action = (CompileTest) myClass.newInstance();
                String str = action.toString();
                System.out.println(str);
            }

            //第二种
            URL url1 = new URL("file:D:/jarload/test.jar");
            URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 }, Thread.currentThread()
                    .getContextClassLoader());
            Class<?> myClass1 = myClassLoader1.loadClass("com.java.jarloader.TestAction");
            CompileTest action1 = (CompileTest) myClass1.newInstance();
            String str1 = action1.toString();
            System.out.println(str1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
