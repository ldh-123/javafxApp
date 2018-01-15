package daymac;

import javafx.application.Application;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompileTest {

    public static void main(String[] args) throws Exception {
        String sOutputPath = "E:\\project\\musicApp\\bootstrapUi\\target\\classes";
        List<String> paths = new ArrayList<String>();
        paths.add("E:\\test\\LoginPageTest.java");

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        JavaFileManager.Location oLocation = StandardLocation.CLASS_OUTPUT;
        fileManager.setLocation(oLocation, Arrays.asList(new File[] { new File(sOutputPath) }));
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(paths);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
        boolean result = task.call();
        fileManager.close();

        Application.launch((Class<? extends Application>) Class.forName("test.LoginPageTest"), args);
    }
}
