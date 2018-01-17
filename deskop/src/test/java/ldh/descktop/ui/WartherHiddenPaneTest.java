package ldh.descktop.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import ldh.fx.component.LHiddenPane;

/**
 * Created by ldh on 2018/1/16.
 */
public class WartherHiddenPaneTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label label = new Label("天气");
        WebView webView = new WebView();

        webView.setOnMousePressed(e->{});
        webView.getEngine().load("http://www.seniverse.com/weather/weather.aspx?uid=U43DF172E7&amp;cid=CHBJ000000&amp;l=&amp;p=SMART&amp;a=1&amp;u=C&amp;s=13&amp;m=2&amp;x=1&amp;d=1&amp;fc=&amp;bgc=2E93D9&amp;bc=&amp;ti=0&amp;in=0&amp;li=");
        webView.getEngine().setUserStyleSheetLocation(WartherHiddenPaneTest.class.getResource("/css/hidden.css").toExternalForm());

        LHiddenPane hiddenPane = new LHiddenPane(label, webView);
        hiddenPane.setPrefSize(200, 200);

        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().add(hiddenPane);


        Scene scene = new Scene(flowPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.show();
    }
}
