package page;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by xiongfei.lei on 2017/12/20.
 */
public class JsTest extends Application {

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create a script engine manager
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a Nashorn script engine
        ScriptEngine engine = factory.getEngineByName("javascript");
        // evaluate JavaScript statement
        try {
            String js = new String(Files.readAllBytes(Paths.get(JsTest.class.getResource("/js/demo2.js").toURI())));
            engine.eval(js);
            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;
                invoke.invokeFunction("start", primaryStage);// 调用对应方法，并传入
                //1个参数:需要处理的 字符串符串
            }
//            System.in.read();
        } catch (final ScriptException se) { se.printStackTrace(); }
    }
}
