package ldh.descktop.page;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ldh.descktop.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ldh on 2018/4/20.
 */
public class JsonPane extends BorderPane {

    private TextArea jsonTextArea = new TextArea();

    public JsonPane() {
        jsonTextArea.setStyle("-fx-font-size: 18");
        this.setCenter(jsonTextArea);

        buildBottom();
    }

    private void buildBottom() {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(5, 5, 5, 5));
        Button parseFormatBtn = new Button("格式化");
        Button clearBtn = new Button("清空");
        parseFormatBtn.setOnAction(this::parseJson);
        clearBtn.setOnAction(e->jsonTextArea.setText(""));
        hbox.getChildren().addAll(parseFormatBtn, clearBtn);

        this.setBottom(hbox);
    }

    private void parseJson(ActionEvent actionEvent) {
        String content = jsonTextArea.getText();
        if (!StringUtils.isEmpty(content)) {
            jsonTextArea.setText(JsonUtil.formatter(content));
        }
    }
}
