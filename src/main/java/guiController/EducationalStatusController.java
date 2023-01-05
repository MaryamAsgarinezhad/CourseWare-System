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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

public class EducationalStatusController implements Initializable {
    static Logger logger = Logger.getLogger(loginController.class);
    @FXML
    Button menu;
    @FXML
    Label l1;
    @FXML
    Label l2;
    @FXML
    Label l3;
    @FXML
    Label AvNumber;
    @FXML
    Label Av;
    @FXML
    Label unitsNumber;
    @FXML
    Label units;

    @FXML
    VBox vv;
    @FXML
    TextField tt;
    @FXML
    Button bb;
    @FXML
    Label mssg;
    @FXML
    HBox hbox;
    @FXML
    VBox v1;
    @FXML
    VBox v2;
    @FXML
    VBox v3;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vv.setVisible(false);
        if(OfflineMode.getInstance().isOffline()){
            String s= Jackson.getjsonOfFile(User.getInstance(null).getJsonObject().get("username").toString()).get("educationalStatusPage").toString();

            ObjectMapper objectMapper=Jackson.getNetworkObjectMapper();
            try {
                List<HashMap<String ,Object>> ans=objectMapper.readValue(s,List.class);
                System.out.println(ans+"3");
                initiateEducationalStatusPage(ans);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if(OfflineMode.getInstance().getMainMenu().get("username").toString().substring(0,1).equals("v")){
            vv.setVisible(true);
            return;
        }

        Response response = Client.getInstanse(0).getServerController().sendEducationalStatusRequest((String) OfflineMode.getInstance().getMainMenu().get("username"));
        OfflineMode.getInstance().setEducationalStatus(response.getAnswer());

        List<HashMap<String ,Object>> S=OfflineMode.getInstance().getEducationalStatus();

        initiateEducationalStatusPage(S);
    }
    private void initiateEducationalStatusPage(List<HashMap<String, Object>> S) {
        int a=0;
        float b=0;

        for (HashMap<String ,Object> item:S) {
            for (int i = 1; i < 4; i++) {
                if(i==1){
                    TextField t=new TextField();
                    if(item.get("grade").toString().equals("()")){
                        break;
                    }

                    t.setText(item.get("grade").toString());

                    String[] s=t.getText().split("\\(");

                    if(s[1].substring(0,1).equals("t")){
                        t.setText("N/A");
                    }
                    else{
                        a+=Double.parseDouble(item.get("unit").toString());
                        b+=Double.parseDouble(item.get("unit").toString()) * Double.parseDouble(s[0]);
                    }

                    v1.getChildren().add(t);
                }
                if(i==2){
                    TextField t=new TextField();
                    t.setText(item.get("unit").toString());
                    v2.getChildren().add(t);
                }
                if(i==3){
                    TextField t=new TextField();
                    t.setText(item.get("CourseName").toString());
                    v3.getChildren().add(t);
                }
            }
        }

        unitsNumber.setText(String.valueOf(a));
        AvNumber.setText(String.format("%5s",b/a));
    }

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
    public void apply(ActionEvent event) {
        clearTable();
        if(tt.getText().equals("")){
            mssg.setText("Enter student username");
        }
        else{
            Response response = Client.getInstanse(0).getServerController().sendEducationalStatusRequest(tt.getText());
            OfflineMode.getInstance().setEducationalStatus(response.getAnswer());

            List<HashMap<String ,Object>> S=OfflineMode.getInstance().getEducationalStatus();

            initiateEducationalStatusPage(S);
        }
    }
    private void clearTable() {
        v3.getChildren().clear();
        v2.getChildren().clear();
        v1.getChildren().clear();
    }
}
