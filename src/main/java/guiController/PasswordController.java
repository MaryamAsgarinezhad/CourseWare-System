package guiController;

import Logic.OfflineMode;
import client.Client;
import client.util.Response;
import client.util.ResponseStatus;
import com.example.demo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;

public class PasswordController {
    static Logger logger = Logger.getLogger(loginController.class);
    @FXML
    Label l;
    @FXML
    TextField t1;
    @FXML
    TextField t2;
    @FXML
    Label mssg;
    @FXML
    Button b;
    public void chnge(ActionEvent e) {
        HashMap<String ,Object> j= OfflineMode.getInstance().getMainMenu();

        Response response1=Client.getInstanse(0).getServerController().getPreviousPass(j.get("username"));
        String password=response1.getData().get("pass").toString();

        if(t1.getText().equals("") || t2.getText().equals("")){
            mssg.setText("fill both fields!");
        }
        else{
            if(!t1.getText().equals(password)){
                mssg.setText("previous password is incorrect!");
            }
            else{
                Response response= Client.getInstanse(0).getServerController().chngPass(j.get("username"),t2.getText());

                if(response.getStatus()== ResponseStatus.OK){
                    mssg.setText("pass changed!");
                    logger.info("pass changed!");

                    //go to login
                    try {
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());

                        stage.setScene(scene);
                        stage.show();
                        logger.info("Entered Main menu successfully");

                    } catch (IOException ex) {
                        logger.error("couldnt find main menu page");
                    }
                }
                else{
                    mssg.setText("pass didnt change.");
                    logger.info("pass didnt change.");
                }
            }
        }
    }
}
