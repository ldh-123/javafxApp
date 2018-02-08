package com.modulefx.test.view;

import javafx.application.Application;
import javafx.event.Event;
import ldh.felix.FelixMain;
import org.osgi.framework.*;

public class Activator implements BundleActivator {

    @Override
    public void start(final BundleContext bundle) throws Exception {

        new Thread() {
            @Override
            public void run() {
                System.out.println("start!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Application.launch(com.modulefx.test.view.App.class);

                App.stage.setOnCloseRequest(e->{
                    try {
                        System.out.println("stop!!!!!!!!!!!!00000!!!!!!!!!!!!!!!!!!!!");
                        FelixMain.m_fwk.stop();
                        System.out.println("stop!!!!!!!!!!!!!!!11111!!!!!!!!!!!!!!!!!");
                        FelixMain.m_fwk.waitForStop(0L);
                        System.out.println("stop!!!!!!!!!!!!!!!!!!2222!!!!!!!!!!!!!!");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
            };
        }.start();

    }

    @Override
    public void stop(BundleContext arg0) throws Exception {
    }
}
