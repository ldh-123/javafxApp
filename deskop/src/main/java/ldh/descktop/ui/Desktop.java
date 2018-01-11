package ldh.descktop.ui;

import javafx.scene.layout.BorderPane;
import sun.security.krb5.internal.crypto.Des;

/**
 * Created by ldh on 2018/1/11.
 */
public class Desktop extends BorderPane {

    private DesktopToolbar toolbar;
    private DesktopPane desktopPane;

    public Desktop(DesktopToolbar toolbar, DesktopPane desktopPane) {
        this.toolbar = toolbar;
        this.desktopPane = desktopPane;

        initUi();
    }

    private void initUi() {
        this.setCenter(desktopPane);
        this.setBottom(toolbar);
    }


}
