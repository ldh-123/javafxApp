package ldh.fx;

import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StageUtil {

    public static Stage STAGE = null;
    private static final Logger logger = Logger.getLogger(StageUtil.class.getName());

    private static Map<Stage, Callback<Void, Void>> childrenStageMap = new ConcurrentHashMap<>();

    public static void exit() {
        if (STAGE != null) {
            STAGE.close();
        }
    }

    public static void putNewStage(Stage stage, Callback<Void, Void> callback) {
        childrenStageMap.put(stage, callback);
        stage.setOnCloseRequest(e->{
            closeNewStage(stage);
        });
    }

    public static void closeNewStage(Stage stage) {
        logger.log(Level.INFO, "remove stage0000:" + stage);
        if (childrenStageMap.containsKey(stage)) {
            Callback callback = childrenStageMap.get(stage);
            callback.call(null);
            childrenStageMap.remove(stage);

            logger.log(Level.INFO, "remove stage:" + stage);
        }
    }

    public static void closeAllNewStages() {
        Iterator<Map.Entry<Stage, Callback<Void, Void>>> iterator = childrenStageMap.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<Stage, Callback<Void, Void>> entry = iterator.next();
            Stage stage = entry.getKey();
            logger.log(Level.INFO, "remove stage:" + stage);
            iterator.remove();
            stage.close();
            Callback callback = entry.getValue();
            callback.call(null);
        }
    }
}
