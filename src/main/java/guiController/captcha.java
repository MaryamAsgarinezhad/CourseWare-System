package guiController;

import client.util.Config;
import javafx.scene.image.Image;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class captcha {

    static Logger logger = Logger.getLogger(loginController.class);

    private Image c1;
    private Image c2;
    private Image c3;
    private Image c4;
    private Image c5;
    private Image c6;
    private List<String> pass=new ArrayList<>(Arrays.asList("1738","6138B","7364","6982","2379","4828"));
    public static int capchaCntr=0;

    public List<String> getPass() {
        return pass;
    }

    public captcha() throws FileNotFoundException {
        String str1 = Config.getConfig().getProperty(String.class, "c1");
        InputStream stream1 = new FileInputStream(str1);
        c1=new Image(stream1);

        String str2 = Config.getConfig().getProperty(String.class, "c2");
        InputStream stream2 = new FileInputStream(str2);
        c2=new Image(stream2);

        String str3 = Config.getConfig().getProperty(String.class, "c3");
        InputStream stream3 = new FileInputStream(str3);
        c3=new Image(stream3);

        String str4 = Config.getConfig().getProperty(String.class, "c4");
        InputStream stream4 = new FileInputStream(str4);
        c4=new Image(stream4);

        String str5 = Config.getConfig().getProperty(String.class, "c5");
        InputStream stream5 = new FileInputStream(str5);
        c5=new Image(stream5);

        String str6 = Config.getConfig().getProperty(String.class, "c6");
        InputStream stream6 = new FileInputStream(str6);
        c6=new Image(stream6);
    }

    public Image getImage(int i){

        logger.info("new captcha picture set");

        switch(i){
            case 0:
                return c1;

            case 1:
                return c2;

            case 2:
                return c3;

            case 3:
                return c4;

            case 4:
                return c5;

            case 5:
                return c6;

            default:
                return c1;
        }
    }

}
