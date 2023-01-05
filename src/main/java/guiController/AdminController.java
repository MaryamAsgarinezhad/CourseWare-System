package guiController;

import Logic.OfflineMode;
import client.Client;
import client.util.Jackson;
import client.util.Response;
import com.example.demo.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    @FXML
    VBox repVbox;
    @FXML
    TextField adminTextField;
    @FXML
    Label mssg;

    public void menu(ActionEvent e) {
        if(!OfflineMode.getInstance().isOffline()){
            Response response = Client.getInstanse(0).getServerController().sendLoginRequest((String) OfflineMode.getInstance().getMainMenu().get("username"), "mainmenu");
            //setting offline features
            OfflineMode.getInstance().setMainMenu(response.getData());
        }
        else{
            if(!OfflineMode.getInstance().isOfflineFromFirst()){
                String username=OfflineMode.getInstance().getMainMenu().get("username").toString();
                JSONObject jsonObject= Jackson.getjsonOfFile(username);
                User.setInstance(new User(jsonObject));
            }
        }

        try {
            Stage stage = ((Stage) (((Node) (e.getSource())).getScene()).getWindow());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setScene(scene);
            stage.show();
            logger.info("Back to main menu");
        } catch (IOException ex) {
            logger.info("couldnt go Back to main menu");
        }
    }
    public void sendToAdmin(ActionEvent actionEvent) {
        if(adminTextField.getText().equals("")){
            mssg.setText("Fill adminTextField!");
        }
        else{
            if(OfflineMode.getInstance().isOffline()){
                SetOfflineMessage(adminTextField.getText());
            }
            else{
                String message=adminTextField.getText();
                Client.getInstanse(0).getServerController().SendToAmin(message,OfflineMode.getInstance().getMainMenu().get("username"));
                mssg.setText("sent successfully, wait for response!");
            }
        }
    }

    private void SetOfflineMessage(String message) {
        String username=User.getInstance(null).getJsonObject().get("username").toString();

        String s=Jackson.getjsonOfFile(username).get("admin").toString();
        ObjectMapper objectMapper=Jackson.getNetworkObjectMapper();

        try {
            List<String > list=objectMapper.readValue(s,List.class);
            list.add(message);

            String value=objectMapper.writeValueAsString(list);
            Jackson.writeOnFile(value,username,"admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(OfflineMode.getInstance().isOffline()){
            return;
        }
        Response response=Client.getInstanse(0).getServerController().getAdminResponseToStudent(OfflineMode.getInstance().getMainMenu().get("username"));
        HashMap<String ,Object> C=response.getData();

        initializePage(C);
    }
    private void initializePage(HashMap<String, Object> C) {
        for (Object temp:C.values()){
            if(((String)temp).equals("")){
                continue;
            }
            TextField t=new TextField();
            t.setText((String) temp);
            repVbox.getChildren().add(t);
        }
    }
}
