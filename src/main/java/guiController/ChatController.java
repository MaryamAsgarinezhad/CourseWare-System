package guiController;

import Logic.OfflineMode;
import client.Client;
import client.util.FileConverter;
import client.util.Jackson;
import client.util.Response;
import com.example.demo.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ChatController implements Initializable {
    String currentUsername;
    int indexOfChatsToBeRemoved;
    static Logger logger = Logger.getLogger(loginController.class);
    List<String> usernames=new ArrayList<>();
    @FXML
    TextField foreignContact;
    @FXML
    ListView listView;
    @FXML
    VBox vbox1;
    @FXML
    ImageView im;
    @FXML
    Label ContactFullname;
    @FXML
    TextField mssgBox;
    @FXML
    VBox chatBox;
    @FXML
    Label mssg;
    @FXML
    Button s;
    @FXML
    Button ss;
    @FXML
    VBox vvv;

    public void startChat(ActionEvent actionEvent) {
        String yours=OfflineMode.getInstance().getMainMenu().get("username").toString();
        for (Object item:listView.getSelectionModel().getSelectedItems()){
            String theirs=((String) item).substring(1,4);
            currentUsername=theirs;

            String mssg=NameOfUser(yours)+" wants to send you a message!";

            send(yours,theirs,mssg);
        }
        newChatInit();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(OfflineMode.getInstance().isOffline()){
            mssgBox.setVisible(false);
            s.setVisible(false);
            ss.setVisible(false);
            vvv.setVisible(false);

            ObjectMapper objectMapper=Jackson.getNetworkObjectMapper();
            String username=User.getInstance(null).getJsonObject().get("username").toString();

            String s= Jackson.getjsonOfFile(username).get("chatPage").toString();
            try {
                System.out.println(s);

                HashMap<String ,Object> ans=objectMapper.readValue(s,HashMap.class);
                clearTable();
                initiatePage(ans);
                return;
            } catch (IOException e) {
                logger.error("error offline");
            }
        }
        //new chat
        newChatInit();

        //current chats
        Response response= Client.getInstanse(0).getServerController().getAllChats(OfflineMode.getInstance().getMainMenu().get("username"));
        HashMap<String ,Object> C=response.getData();

        OfflineMode.getInstance().setChat(response.getData());
        currentChatsInit(C);
    }
    private void currentChatsInit(HashMap<String,Object> C) {
        clearTable();
        initiatePage(C);
    }
    private void clearTable() {
        vbox1.getChildren().clear();
        chatBox.getChildren().clear();

        usernames.clear();
    }
    private void newChatInit() {
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Response response1= Client.getInstanse(0).getServerController().newChatContacts(OfflineMode.getInstance().getMainMenu().get("username"));

        if (response1==null){
            return;
        }
        ObservableList<String> a= FXCollections.observableArrayList();
        for (Object temp:response1.getData().values()){
            a.add((String) temp);
        }
        listView.setItems(a);
    }
    private void initiatePage(HashMap<String, Object> CC) {
        CC.remove(CC.keySet().toArray()[0]);
        HashMap<String, Object> C=SortByLastMessageSent(CC);

        int cntr=-1;
        for (Object temp:C.values()){
            if(temp.equals("")){
                continue;
            }
            cntr++;
            String[] trueMessage=((String)temp).split("::->");
            List<String> messageList=getMessageList(trueMessage[1]);

            Label label=new Label();
            label.setMinWidth(290);
            label.setText(NameOfUser(trueMessage[0]));
            usernames.add(trueMessage[0]);

            vbox1.getChildren().add(label);

            Button b=new Button();
            b.setMinWidth(290);

            String[] s=messageList.get(messageList.size()-1).split("--");
            if(s[0].length()>14){
                if(s[0].substring(0,12).equals("TypeOfFile->")){
                    String[] t=s[0].split("::::");
                    String[] ttt=t[1].split("  ");
                    String[] tt=t[0].split(">");
                    String typeOfFile=tt[1];

                    b.setText(typeOfFile+" file  "+ttt[1]);
                    b.setId(String.valueOf(cntr));
                    b.setOnAction(event -> ShowChat((Button) event.getSource()));
                    vbox1.getChildren().add(b);
                    continue;
                }
            }
            b.setText(s[0]);
            b.setId(String.valueOf(cntr));
            b.setOnAction(event -> ShowChat((Button) event.getSource()));

            vbox1.getChildren().add(b);
        }
    }
    private HashMap<String, Object> SortByLastMessageSent(HashMap<String, Object> CC) {
        HashMap<String, Object> ans=new HashMap<>();
        int length=CC.size();
        int key;

        for (int i = 0; i < length; i++) {
            Object temporary=CC.get(CC.keySet().toArray()[0]);
            key=Integer.parseInt((String) CC.keySet().toArray()[0]);
            for (Object temp:CC.values()){
                if(compare(temp).compareTo(compare(temporary))>0){
                    key=findIndexInMap(CC,temp);
                    temporary=temp;
                }
            }
            ans.put(String.valueOf(i+1),temporary);
            CC.remove(String.valueOf(key));
        }
        return ans;
    }
    private int findIndexInMap(HashMap<String ,Object> map, Object object){
        for (Map.Entry<String ,Object> temp:map.entrySet()){
            if(temp.getValue()==object){
                return Integer.valueOf(temp.getKey());
            }
        }
        return -1;
    }
    private String compare(Object temp){
        String[] trueMessage=((String)temp).split("::->");
        List<String> messageList=getMessageList(trueMessage[1]);

        String[] s=messageList.get(messageList.size()-1).split("--");
        return s[0].substring(s[0].length()-8);
    }
    private String NameOfUser(String s) {
        if(OfflineMode.getInstance().isOffline()){
            return Jackson.getjsonOfFile(s).get("FirstName")+" "+Jackson.getjsonOfFile(s).get("LastName");
        }
        Response response=Client.getInstanse(0).getServerController().getNameOfUser(s);
        return response.getErrorMessage();
    }
    private void ShowChat(Button source) {
        int id=Integer.parseInt(source.getId());

        String username=usernames.get(id);
        currentUsername=username;

        showwchat(username);
    }
    private void showwchat(String username) {
        if(OfflineMode.getInstance().isOffline()){

            String user=User.getInstance(null).getJsonObject().get("username").toString();
            ObjectMapper objectMapper=Jackson.getNetworkObjectMapper();
            String s= Jackson.getjsonOfFile(user).get("chatPage").toString();
            try {
                HashMap<String ,Object> ans=objectMapper.readValue(s,HashMap.class);
                String chat=findChatFromHeader(ans,username);
                ShowChatComponents(chat);
                return;
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //getting data from server
        Response response= Client.getInstanse(0).getServerController().getAllChats(OfflineMode.getInstance().getMainMenu().get("username"));
        HashMap<String ,Object> C=response.getData();

        OfflineMode.getInstance().setChat(response.getData());
        //finding chat from data C
        String chat=findChatFromHeader(C,username);
        ShowChatComponents(chat);
    }
    private void ShowChatComponents(String chat) {
        String username;
        if(OfflineMode.getInstance().isOffline()){
            username=User.getInstance(null).getJsonObject().get("username").toString();
        }
        else{
            username=OfflineMode.getInstance().getMainMenu().get("username").toString();
        }
        chatBox.getChildren().clear();

        String[] s=chat.split("::->");
        ContactFullname.setText(NameOfUser(s[0]));
        try {
            im.setImage(new PersonImage().getImage("man"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<String> mssgList=getMessageList(s[1]);
        for (String temp:mssgList){
            String[] ss=temp.split("--");

            if(ss[0].length()>14){
                if(ss[0].substring(0,12).equals("TypeOfFile->")){
                    String[] t=ss[0].split("::::");
                    String[] ttt=t[1].split("  ");
                    String stringOfFile=ttt[0];
                    String[] tt=t[0].split(">");
                    String typeOfFile=tt[1];

                    Button button=new Button();
                    button.setMinWidth(400);
                    button.setText("download "+typeOfFile+"  "+ttt[1]);
                    button.setId(stringOfFile);
                    button.setOnAction(event -> Download((Button) event.getSource()));

                    if(!ss[1].equals(username)){
                        button.setAlignment(Pos.CENTER_LEFT);
                        button.setTextFill(Color.color(1,0,0));
                    }
                    else{
                        button.setAlignment(Pos.CENTER_RIGHT);
                    }
                    chatBox.getChildren().add(button);
                    continue;
                }
            }
            Label label=new Label();
            label.setMinWidth(390);
            label.setText(ss[0]);

            if(!ss[1].equals(username)){
                label.setAlignment(Pos.CENTER_LEFT);
                label.setTextFill(Color.color(1,0,0));
            }
            else{
                label.setAlignment(Pos.CENTER_RIGHT);
            }
            chatBox.getChildren().add(label);
        }
    }
    private void Download(Button source) {
        String[] s=source.getText().split(" ");
        String typeOfFile=s[1];
        String stringOfFile=source.getId();
        byte[] bytesOfFile=FileConverter.decode(stringOfFile);

        downloadToSpecificPath(typeOfFile,bytesOfFile);
    }
    private void downloadToSpecificPath(String typeOfFile, byte[] bytesOfFile) {
        switch (typeOfFile) {
            case "jpg" -> {
                try {
                    OutputStream downloads=new FileOutputStream("ax.jpg");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            case "png" -> {
                try {
                    OutputStream downloads=new FileOutputStream("solier.png");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            case "pdf" -> {
                try {
                    OutputStream downloads=new FileOutputStream("network.pdf");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case "mp4" -> {
                try {
                    OutputStream downloads=new FileOutputStream("film.mp4");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case "ogg" -> {
                try {
                    OutputStream downloads=new FileOutputStream("voice.ogg");
                    downloads.write(bytesOfFile);
                    downloads.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            default -> {
            }
        }
    }
    private String findChatFromHeader(HashMap<String, Object> C, String header) {
        for (Object temp:C.values()){
            String item=(String) temp;
            String[] s=item.split("::->");
            if(s[0].equals(header)){
                return item;
            }
        }

        return "";
    }
    private List<String> getMessageList(String temp) {
        List<String> ans=new ArrayList<>();

        if(!temp.contains("&&")){
            ans.add(temp);
            return ans;
        }

        String[] s=temp.split("&&");
        for (String temp1:s){
            ans.add(temp1);
        }
        return ans;
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
    public void sendMssg(ActionEvent actionEvent) {
        if(mssgBox.getText().equals("")){
            mssg.setText("Write something before sending!");
        }
        else{
            String yourUsername=OfflineMode.getInstance().getMainMenu().get("username").toString();
            String theirUsername=currentUsername;
            String mssg=mssgBox.getText();

            send(yourUsername,theirUsername,mssg);
            mssgBox.setText("");
        }
    }
    public void send(String yourUsername,String theirUsername,String mssg){
        //send to server
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH;mm;ss");
        LocalDateTime now = LocalDateTime.now();
        String lastTime=dtf.format(now);
        mssg+="  "+lastTime;

        Response response=Client.getInstanse(0).getServerController().sendFromMeToThem(yourUsername,theirUsername,mssg);
        //reload page
        HashMap<String ,Object> C=response.getData();

        OfflineMode.getInstance().setChat(response.getData());
        currentChatsInit(C);

        showwchat(currentUsername);
    }
    public void sendToForeignContact(ActionEvent actionEvent) {
        if(foreignContact.getText().equals("")){
            mssg.setText("Enter new contact username!");
            return;
        }
        else {
            //preparing req
            String header="";
            String rep="";
            header="MessageRequest"+"&&"+OfflineMode.getInstance().getMainMenu().get("username")+"&&"+foreignContact.getText();

            //sending req
            Response response=Client.getInstanse(0).getServerController().sendReqForBothProfAndStu(header,rep);
            mssg.setText("Sent successfully, wait for reply!");
        }
    }
    public void sendFile(ActionEvent actionEvent) {
        if(mssgBox.getText().equals("")){
            mssg.setText("Enter URL!");
        }
        else{
            String path=mssgBox.getText();
            String[] s=path.split("\\.");
            String fileType=s[s.length-1];

            String yourUsername=OfflineMode.getInstance().getMainMenu().get("username").toString();
            String theirUsername=currentUsername;
            String mssg="TypeOfFile->"+fileType+"::::"+ FileConverter.encode(path);

            send(yourUsername,theirUsername,mssg);
            mssgBox.setText("");
        }
    }
}
