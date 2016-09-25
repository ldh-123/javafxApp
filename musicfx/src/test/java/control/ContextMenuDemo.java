package control;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ContextMenuDemo extends Application {

    @Override
    public void start(Stage primaryStage) {

        final ContextMenu cm = new ContextMenu();


        MenuItem menuItem1 = getMenuItemForLine("line 1");
        MenuItem menuItem2 = getMenuItemForLine("line 2");
        MenuItem menuItem3 = getMenuItemForLine("line 3");

        menuItem1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    System.out.println("Desired Click");
                } else {
                    System.out.println("No right click");
                }
                e.consume();
            }
        });

        menuItem2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    System.out.println("Desired Click");
                } else {
                    System.out.println("No right click");
                }
                e.consume();
            }
        });

        menuItem3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    System.out.println("Desired Click");
                } else {
                    System.out.println("No right click");
                }
                e.consume();
            }
        });


        cm.getItems().add(menuItem1);
        cm.getItems().add(menuItem2);
        cm.getItems().add(menuItem3);

        final Rectangle rectangle = new Rectangle(70, 70, Color.TAN);
        rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.SECONDARY) {
                    cm.show(rectangle, e.getScreenX(), e.getScreenY());
                } else {
                    System.out.println("No right click");
                }
            }
        });

        Group root = new Group();
        root.getChildren().addAll(rectangle);
        Scene scene = new Scene(root, 350, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuItem getMenuItemForLine(String menuName) {
        Label menuLabel = new Label(menuName);
        MenuItem menuItem = new MenuItem();
        menuItem.setGraphic(menuLabel);
        return menuItem;
    }

    public static void main(String[] args) {
        launch(args);
    }
}