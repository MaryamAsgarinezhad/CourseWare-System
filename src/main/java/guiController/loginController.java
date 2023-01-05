package guiController;

import Logic.OfflineMode;
import client.Client;
import client.util.Jackson;
import client.util.Response;
import client.util.ResponseStatus;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    static Logger logger = Logger.getLogger(loginController.class);

    private String username;

    @FXML
    TextField nameField;
    @FXML
    TextField passwordField;
    @FXML
    TextField CapchaField;
    @FXML
    Label welcomeLabel;
    @FXML
    Label mssgField;
    @FXML
    Button loginBtn;
    @FXML
    ImageView capcha;

    public void login(ActionEvent e) throws InterruptedException, FileNotFoundException {

        Response response=mainLogin();
        if (response!=null) {
            if(response.getStatus()!= ResponseStatus.ERROR){
                logger.info("User could successfully pass the login conditions");
                mssgField.setText("User could successfully pass the login conditions");
                //setting offline features
                if(!OfflineMode.getInstance().isOffline()){
                    OfflineMode.getInstance().setMainMenu(response.getData());
                    username=OfflineMode.getInstance().getMainMenu().get("username").toString();

                    if(OfflineMode.getInstance().getMainMenu().get("isProfessor").equals("false")){
                        sendCacheToAdmin();
                    }
                }
                else{
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH;mm;ss");
                    LocalDateTime now = LocalDateTime.now();
                    String nowStr=dtf.format(now);

                    Jackson.writeOnFile(nowStr,username,"lastEnterTime");
                }

                //Loading menu page
                Stage stage=new Stage();
                if(username.substring(0,1).equals("M")){
                    try {
                        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Mohseni.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());

                        stage.setScene(scene);
                        stage.show();
                        logger.info("Entered Main menu successfully");

                    } catch (IOException ex) {
                        logger.error("couldnt find mohseni menu page");
                    }
                }
                else{
                    try {
                        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());

                        stage.setScene(scene);
                        stage.show();
                        logger.info("Entered Main menu successfully");

                    } catch (IOException ex) {
                        logger.error("couldnt find main menu page");
                    }
                }

                //chng pass
//                Instant start = Instant.now();
//                while (true){
//                    TimeUnit. SECONDS.sleep((long) 1.0);
//                    Instant end = Instant.now();
//                    Duration timeElapsed = Duration.between(start, end);
//                    System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
//
//                    if(timeElapsed.toMillis()>2500){
//                        try {
//                            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("password.fxml"));
//                            Scene scene = new Scene(fxmlLoader.load());
//
//                            stage.setScene(scene);
//                            stage.show();
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//                        break;
//                    }
//                }
            }
            else{
                mssgField.setText("Username or password is wrong!");
            }
        }
    }

    private void sendCacheToAdmin() {
        String usrname=OfflineMode.getInstance().getMainMenu().get("username").toString();
        ObjectMapper objectMapper=Jackson.getNetworkObjectMapper();
        String s=Jackson.getjsonOfFile(usrname).get("admin").toString();

        System.out.println(s);

        try {
            List<String > list=objectMapper.readValue(s,List.class);
            for (String temp:list){
                Client.getInstanse(0).getServerController().SendToAmin(temp,usrname);
            }

            List<String > neww=new ArrayList<>();
            String newStr=objectMapper.writeValueAsString(neww);
            Jackson.writeOnFile(newStr,usrname,"admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response mainLogin() throws FileNotFoundException {
        String username = nameField.getText();
        String password = passwordField.getText();
        String capchaPass=CapchaField.getText();

        if (username.equals("") || password.equals("") || capchaPass.equals("")) {
            mssgField.setText("enter username , password and captcha");
            return null;
        }
        else {

            if (!capchaPass.equals(new captcha().getPass().get(captcha.capchaCntr))) {
                mssgField.setText("captcha wrong");
                captcha.capchaCntr = (captcha.capchaCntr + 1) % 6;
                capcha.setImage(new captcha().getImage(captcha.capchaCntr));
                return null;
            } else {
                captcha.capchaCntr = 0;
            }
        }

        if(OfflineMode.getInstance().isOffline()){
            JSONObject jsonObject=Jackson.getjsonOfFile(username);

            System.out.println(jsonObject.get("username").toString());
            if(jsonObject.get("password").equals(password)){
                this.username=username;
                User.setInstance(new User(jsonObject));
                return new Response(ResponseStatus.OK);
            }
            else{
                return new Response(ResponseStatus.ERROR);
            }
        }
        else{
            Response response = Client.getInstanse(0).getServerController().sendLoginRequest(username, password);
            return response;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String log4jConfigFile = System.getProperty("user.dir")
                + File.separator + "log4j.properties";

        logger.info("Initializing login components");
        try {
            capcha.setImage(new captcha().getImage(captcha.capchaCntr));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
