package guiController;

import client.util.Config;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PersonImage {

    private Image man;
    private Image woman;

    public PersonImage() throws FileNotFoundException {
        String manStr = Config.getConfig().getProperty(String.class, "manImage");
        String womanStr = Config.getConfig().getProperty(String.class, "womanImage");

        InputStream streamMan = new FileInputStream(manStr);
        man=new Image(streamMan);

        InputStream streamWoman = new FileInputStream(womanStr);
        woman=new Image(streamWoman);
    }

    public Image getImage(String s){
        switch(s){
            case "man":
                return man;

            case "woman":
                return woman;

            default:
                return woman;
        }
    }
}
