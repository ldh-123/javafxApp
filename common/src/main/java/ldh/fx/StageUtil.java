package ldh.fx;

import javafx.stage.Stage;

public class StageUtil {

    public static Stage STAGE = null;

    public static void exit() {
        if (STAGE != null) {
            STAGE.close();
        }
    }
}
