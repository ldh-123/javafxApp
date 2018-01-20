/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.fx.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import ldh.fx.ChangeSizePane;

/**
 *
 * @author Puhui
 */
public class LWindow extends BorderPane implements javafx.fxml.Initializable, ChangeSizePane {
    
    @FXML
    VBox leftPane;
    @FXML
    VBox rightPane;
    @FXML
    HBox titlePane;
    @FXML
    Rectangle nEPane;
    @FXML
    Rectangle bottomPane;
    @FXML
    Rectangle nWPane;
    
    private double startMoveX = -1;
    private double startMoveY = -1;
    private Boolean dragging = false;
    private Rectangle moveTrackingRect;
    private Popup moveTrackingPopup;

    private double lastX = 0.0d;
    private double lastY = 0.0d;
    private double lastWidth = 0.0d;
    private double lastHeight = 0.0d;

    private Stage owner;

    public LWindow(Stage stage, String title, Node node) {
        this(stage, title, node, 800, 500);
    }

    public LWindow(Stage stage, String title, Node node, double width, double height) {
        this.owner = stage;
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ldh/fx/ui/LWindow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        setContent(node);
        bottomPane.widthProperty().bind(this.widthProperty().subtract(nEPane.widthProperty()).subtract(nWPane.widthProperty()));
        Scene scene = new Scene(this, width, height);
        scene.getStylesheets().add("/ldh.fx.css/LWindow.css");
        newStage.setScene(scene);
        newStage.show();
    }
    
    @FXML
    public void close(MouseEvent evt) {
        ((Label)evt.getSource()).getScene().getWindow().hide();
    }

    @FXML
    public void startMoveWindow(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        dragging = true;

        moveTrackingRect = new Rectangle();
        moveTrackingRect.setWidth(this.getWidth());
        moveTrackingRect.setHeight(this.getHeight());
        moveTrackingRect.getStyleClass().add( "tracking-rect" );

        moveTrackingPopup = new Popup();
        moveTrackingPopup.getContent().add(moveTrackingRect);
        moveTrackingPopup.show(this.getScene().getWindow());
        moveTrackingPopup.setOnHidden( (e) -> resetMoveOperation());
    }

    @FXML
    public void moveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = this.getScene().getWindow();
            double stageX = w.getX();
            double stageY = w.getY();
            moveTrackingPopup.setX(stageX + (endMoveX - startMoveX));
            moveTrackingPopup.setY(stageY + (endMoveY - startMoveY));
        }
    }

    @FXML
    public void endMoveWindow(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = this.getScene().getWindow();
            double stageX = w.getX();
            double stageY = w.getY();
            w.setX(stageX + (endMoveX - startMoveX));
            w.setY(stageY + (endMoveY - startMoveY));
            if (moveTrackingPopup != null) {
                moveTrackingPopup.hide();
                moveTrackingPopup = null;
            }
        }
        resetMoveOperation();
    }

    private void resetMoveOperation() {
        startMoveX = 0;
        startMoveY = 0;
        dragging = false;
        moveTrackingRect = null;
    }
    
    @FXML
    public void titleDoubleClick(MouseEvent evt) {
        if (evt.getClickCount() != 2) return;
        maximize(evt);
    }

    @FXML
    public void maximize(MouseEvent evt) {
        Node n = (Node)evt.getSource();
        Window w = n.getScene().getWindow();
        double currentX = w.getX();
        double currentY = w.getY();
        double currentWidth = w.getWidth();
        double currentHeight = w.getHeight();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        if( currentX != bounds.getMinX() &&
                currentY != bounds.getMinY() &&
                currentWidth != bounds.getWidth() &&
                currentHeight != bounds.getHeight() ) {  // if not maximized

            w.setX(bounds.getMinX());
            w.setY(bounds.getMinY());
            w.setWidth(bounds.getWidth());
            w.setHeight(bounds.getHeight());
            
            lastX = currentX;  // save old dimensions
            lastY = currentY;
            lastWidth = currentWidth;
            lastHeight = currentHeight;
        } else {
            w.setX(lastX);
            w.setY(lastY);
            w.setWidth(lastWidth);
            w.setHeight(lastHeight);

        }
        evt.consume();  // don't bubble up to title bar
    }

    @FXML
    public void minimize(MouseEvent evt) {
        Stage stage = (Stage)((Label)evt.getSource()).getScene().getWindow();
        stage.setIconified(true);
        
    }
    
    @FXML
    public void leftChangeSize(MouseEvent evt) {
        leftPane.setCursor(Cursor.H_RESIZE);
    }
    
    @FXML
    public void startChangeLeftSize(MouseEvent evt) {
        this.startChangeSize(evt);
    }
    
    @FXML
    public void changeLeftSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = this.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
  
            w.setX(lastX + changeW);
            w.setWidth(lastWidth - changeW);
        }
    }
    
    @FXML
    public void endChangeLeftSize(MouseEvent evt) {
        this.endChangeSize(evt);
    }
    
    @FXML
    public void rightChangeSize(MouseEvent evt) {
        rightPane.setCursor(Cursor.H_RESIZE);
    }
    
    @FXML
    public void startChangeRightSize(MouseEvent evt) {
        this.startChangeSize(evt);
    }
    
    @FXML
    public void changeRightSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = this.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            w.setWidth(lastWidth + changeW);
        }
    }
    
    @FXML
    public void endChangeRightSize(MouseEvent evt) {
        this.endChangeSize(evt);
    }
    
    @FXML
    public void bottomChangeSize(MouseEvent evt) {
        bottomPane.setCursor(Cursor.V_RESIZE);
    }
    
    @FXML
    public void startChangeBottomSize(MouseEvent evt) {
        this.startChangeSize(evt);
    }
    
    @FXML
    public void changeBottomSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();
            Window w = this.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            w.setHeight(lastHeight + changeH);
        }
    }
    
    @FXML
    public void endChangeBottomSize(MouseEvent evt) {
        endChangeSize(evt);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    public void nEChangeSize(MouseEvent evt) {
        nEPane.setCursor(Cursor.NE_RESIZE);
    }
    
    @FXML
    public void startChangeNESize(MouseEvent evt) {
        this.startChangeSize(evt);
    }
    
    @FXML
    public void changeNESize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = this.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            w.setX(lastX + changeW);
            w.setWidth(lastWidth - changeW);
            w.setHeight(lastHeight + changeH);
        }
    }
    
    @FXML
    public void endChangeNESize(MouseEvent evt) {
        endChangeSize(evt);
    }
    
    @FXML
    public void nWChangeSize(MouseEvent evt) {
        nWPane.setCursor(Cursor.NW_RESIZE);
    }
    
    @FXML
    public void startChangeNWSize(MouseEvent evt) {
        startChangeSize(evt);
    }
    
    @FXML
    public void changeNWSize(MouseEvent evt) {
        if (dragging) {
            double endMoveX = evt.getScreenX();
            double endMoveY = evt.getScreenY();

            Window w = this.getScene().getWindow();
            double changeW = endMoveX - this.startMoveX;
            double changeH = endMoveY - this.startMoveY;
            w.setHeight(lastHeight + changeH);
            w.setWidth(lastWidth + changeW);
        }
    }
    
    @FXML
    public void endChangeNWSize(MouseEvent evt) {
        if (dragging) {
            dragging = false;
        }
    }
    
    public void setContent(Node node) {
        this.setCenter(node);
    }

    @Override
    public void startChangeSize(MouseEvent evt) {
        startMoveX = evt.getScreenX();
        startMoveY = evt.getScreenY();
        dragging = true;
        Window w = this.getScene().getWindow();
        lastHeight = w.getHeight();
        lastWidth = w.getWidth();
        lastX = w.getX();
        lastY = w.getY();
    }

    @Override
    public void endChangeSize(MouseEvent evt) {
        if (dragging) {
            dragging = false;
        }
    }
}
