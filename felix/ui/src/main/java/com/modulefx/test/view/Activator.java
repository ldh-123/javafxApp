package com.modulefx.test.view;

import javafx.application.Application;
import ldh.felix.FelixMain;
import org.osgi.framework.*;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext bundle) throws Exception {

        new Thread() {
            @Override
            public void run() {
                System.out.println("start11111!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Application.launch(com.modulefx.test.view.App.class);
            };
        }.start();
    }

    @Override
    public void stop(BundleContext arg0) throws Exception {
    }
}
