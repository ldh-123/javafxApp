package ldh.descktop.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ldh on 2018/1/11.
 */
public class ThreadToolUtil {

    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    public static void scheduleAtFixedRate(Runnable runnable, long period, TimeUnit timeUnit) {
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, period, timeUnit);
    }

    public static void close() {
        scheduledExecutorService.shutdownNow();
    }
}
