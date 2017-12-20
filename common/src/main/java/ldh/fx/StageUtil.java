package ldh.fx;

import javafx.stage.Stage;

/**
 * Created by xiongfei.lei on 2017/12/18.
 */
public class StageUtil {

    public static Stage STAGE = null;

    public static void exit() {
        if (STAGE != null) {
            STAGE.close();
        }
    }
}
