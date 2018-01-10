package chart.parliament;

import javafx.scene.image.Image;

import java.io.File;

public class Mp {
    private Image photo;
    private String name;
    private String partyName;
    private String colorHTML;

    public Mp(String name, String partyName, File photoFile, String colorHTML) {
        this.name = name;
        this.partyName = partyName;
        photo = new Image(photoFile.toURI().toString());
        this.colorHTML = colorHTML;
    }

    Image getPhoto() {
        return photo;
    }

    public void setPhoto(File photoFile) {
        this.photo = new Image(photoFile.toURI().toString());
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getPartyName() {
        return partyName;
    }

    void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getColorHTML() {
        return colorHTML;
    }

    void setColorHTML(String colorHTML) {
        this.colorHTML = colorHTML;
    }
}
