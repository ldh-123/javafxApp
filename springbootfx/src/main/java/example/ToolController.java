package example;

import de.felixroske.jfxsupport.FXMLController;
import javafx.event.Event;

/**
 * Created by ldh on 2018/4/17.
 */
@FXMLController
public class ToolController {

    public void doSomething(final Event e) {
        System.out.println("You pressed some button!");
    }
}
