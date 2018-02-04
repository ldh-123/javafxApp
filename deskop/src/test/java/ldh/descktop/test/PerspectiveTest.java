package ldh.descktop.test;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by ldh on 2018/2/4.
 */
public class PerspectiveTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
        perspectiveTrasform.setUlx(10.0);
        perspectiveTrasform.setUly(10.0);
        perspectiveTrasform.setUrx(310.0);
        perspectiveTrasform.setUry(40.0);
        perspectiveTrasform.setLrx(310.0);
        perspectiveTrasform.setLry(60.0);
        perspectiveTrasform.setLlx(10.0);
        perspectiveTrasform.setLly(90.0);

        Group g = new Group();
        g.setEffect(perspectiveTrasform);
        g.setCache(true);

        Rectangle rect = new Rectangle();
        rect.setX(10.0);
        rect.setY(10.0);
        rect.setWidth(280.0);
        rect.setHeight(80.0);
        rect.setFill(Color.web("0x3b596d"));

        Text text = new Text();
        text.setX(20.0);
        text.setY(65.0);
        text.setText("Perspective");
        text.setFill(Color.ALICEBLUE);
        text.setFont(Font.font(null, FontWeight.BOLD, 36));

        g.getChildren().addAll(rect, text);
        Scene scene = new Scene(g, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.show();
    }
}
