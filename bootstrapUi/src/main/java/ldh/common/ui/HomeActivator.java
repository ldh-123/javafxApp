package ldh.common.ui;

import javafx.application.Application;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Created by ldh on 2018/1/5.
 */
public class HomeActivator implements BundleActivator {

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        new Thread(()-> Application.launch(JavafxHomeApplication.class)).start();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
