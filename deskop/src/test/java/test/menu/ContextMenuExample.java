package test.menu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * Created by ldh on 2018/1/15.
 */
public class ContextMenuExample extends ContextMenu {



    /** The players. */
    Menu players = new Menu("Play on");

    /** The player 0. */
    MenuItem player0 = new MenuItem("xPlayer ~0");

    /** The player 1. */
    MenuItem player1 = new MenuItem("xPlayer ~1");

    /** The player 2. */
    MenuItem player2 = new MenuItem("xPlayer ~2");

    //END:--Search on Web

    /** The add on. */
    Menu addOn = new Menu("Add on");

    /** The x player 0. */
    MenuItem xPlayer0 = new MenuItem("xPlayer ~0 PlayList");

    /** The x player 1. */
    MenuItem xPlayer1 = new MenuItem("xPlayer ~1 PlayList");

    /** The x player 2. */
    MenuItem xPlayer2 = new MenuItem("xPlayer ~2 PlayList");

    /** The more. */
    Menu more = new Menu("More...");

    /** The information. */
    MenuItem information = new MenuItem("Information (I)");

    /** The stars. */
    MenuItem stars = new MenuItem("Stars (S)");

    /** The source folder. */
    MenuItem sourceFolder = new MenuItem("PathFolder (P)");

    /** The copy. */
    MenuItem copy = new MenuItem("copy/move (C/M)");

    /** The move. */
    //MenuItem move = new MenuItem("moveTo(M)")

    /** The rename. */
    MenuItem rename = new MenuItem("Rename (R)");

    /** The simple delete. */
    MenuItem simpleDelete = new MenuItem("Delete (Delete)");

    /** The storage delete. */
    MenuItem storageDelete = new MenuItem("Delete (Shift+Delete)");

    /**
     * Constructor.
     */
    public ContextMenuExample() {

        //Add all the items
        getItems().addAll(new TitleMenuItem("Common"), players,more, new TitleMenuItem("File Edit"),
                rename, simpleDelete, storageDelete, new TitleMenuItem("Organize"), copy);

        //---play

        players.getItems().addAll(player0, player1, player2);


        // add on deck play list 0,1,2
        addOn.setDisable(true);
        addOn.getItems().addAll(xPlayer0, xPlayer1, xPlayer2);

        // More
        more.getItems().addAll(stars, sourceFolder);
    }



}
