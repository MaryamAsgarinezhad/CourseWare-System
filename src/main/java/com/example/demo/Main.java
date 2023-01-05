package com.example.demo;

import Logic.OfflineMode;
import client.Client;
import client.util.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    private static Stage stage;
    static Logger logger = Logger.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        this.stage=stage;
        //connecting to server
        Integer port = Config.getConfig().getProperty(Integer.class, "serverPort");
        Client client = Client.getInstanse(port);
        client.start();

        //loading login page
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Education system");
        stage.setScene(scene);
        stage.show();

        logger.info("Entered login page succesfully.");
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int i = 1000;

            @Override
            public void run() {
                if (i > 0) {
                    Integer timerPort = Config.getConfig().getProperty(Integer.class, "timerPort");
                    Client client=new Client(timerPort);
                    client.start();
                    Boolean b=client.getServerController().checkConnection();
                    if(!b){
                        System.out.println("Server disconnected!");
                        OfflineMode.getInstance().setOffline(true);
                        i=0;
                    }
                    i--;
                }
                else {
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task,5000,1000);

        logger.info("opening login page");
        launch();
    }
}

