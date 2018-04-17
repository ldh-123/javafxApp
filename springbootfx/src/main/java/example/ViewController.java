package example;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.Event;
import javafx.stage.Modality;

import java.io.IOException;

/**
 * Created by ldh on 2018/4/17.
 */
@FXMLController
public class ViewController {

    public void showToolWindow(Event event) throws IOException {
        Main.showView(ToolView.class, Modality.NONE);
    }
}
