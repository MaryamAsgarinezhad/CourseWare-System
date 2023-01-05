package server;

import server.util.Config;

public class ServerMain {
    public static void main(String[] args) {
        Integer port = Config.getConfig().getProperty(Integer.class, "serverPort");
        Server server = new Server(port);
        Server.running=true;
        server.start();
    }
}
