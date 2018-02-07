package com.modulefx.test.view;

import javafx.application.Application;
import javafx.event.Event;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext bundle) throws Exception {

        new Thread() {
            @Override
            public void run() {
                System.out.println("start!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Application.launch(com.modulefx.test.view.App.class);
            };
        }.start();

    }

    @Override
    public void stop(BundleContext arg0) throws Exception {
    }
}
