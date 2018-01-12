package ldh.descktop.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ldh.descktop.util.ThreadToolUtil;

/**
 * Created by ldh on 2018/1/12.
 */
public class CalacUiTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(50));
        vbox.getChildren().add(new Label("test"));
        vbox.setSpacing(20);
        Button b = new Button("button");
        b.setOnAction(e->{
            StackPane stackPane = new StackPane();
            Label label = new Label("asdfad");
            stackPane.getChildren().add(label);
            size(stackPane);
            double h = stackPane.getLayoutBounds().getHeight();
            System.out.println("size: " + stackPane.getHeight() + ", hh:" + h);
        });
        vbox.getChildren().add(b);
        Scene scene = new Scene(vbox, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("demo");
        primaryStage.show();
    }

    private void size(Node node) {
        final Node popupContent = node;

        if (popupContent instanceof Region) {
            // snap to pixel
            final Region r = (Region) popupContent;

            // 0 is used here for the width due to RT-46097
            double prefHeight = snapSize(r.prefHeight(0));
            double minHeight = snapSize(r.minHeight(0));
            double maxHeight = snapSize(r.maxHeight(0));
            double h = snapSize(Math.min(Math.max(prefHeight, minHeight), Math.max(minHeight, maxHeight)));

            System.out.println("height:" + h);
            double prefWidth = snapSize(r.prefWidth(h));
            double minWidth = snapSize(r.minWidth(h));
            double maxWidth = snapSize(r.maxWidth(h));
            double w = snapSize(Math.min(Math.max(prefWidth, minWidth), Math.max(minWidth, maxWidth)));

            popupContent.resize(w, h);
        } else {
            popupContent.autosize();
        }
    }

    private double snapSize(double value) {
        return value;
    }
}
