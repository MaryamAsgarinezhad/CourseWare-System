package client;

import client.network.ServerController;

import java.util.Scanner;

public class Client {

    public static Client instanse;
    private final Scanner scanner = new Scanner(System.in);

    private ServerController serverController;
    private final int port;

    public static Client getInstanse(int port){
        if(instanse==null){
            instanse=new Client(port);
            return instanse;
        }
        else {
            return instanse;
        }
    }

    public Client(int port) {
        this.port = port;
    }

    public static Client getInstanse(int port,boolean isSecond) {
        return new Client(port);
    }

    public void start() {
        serverController = new ServerController(port);
        serverController.connectToServer();
        //loginCLI
    }

    public ServerController getServerController() {
        return serverController;
    }
}
