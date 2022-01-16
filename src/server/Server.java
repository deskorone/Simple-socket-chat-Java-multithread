package server;

import connection.Connection;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class Server {
    Logger logger = Logger.getLogger(Server.class);
    FileManager manager;
    ServerSocket serverSocket;
    UserList userList = new UserList();
    public static void main(String[] args) {
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Server() throws IOException {
        this.manager = new FileManager();
        this.serverSocket = new ServerSocket(9090);
        logger.info("Server start");
        while (true){
               new ServerThread(serverSocket.accept()).start();
        }
    }
    public class ServerThread extends Thread{
        Socket socket;
        Connection connection;

        ServerThread(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){
            try(Connection connection = new Connection(socket)) {
                Logic logic = new Logic(connection, manager);
                while(true) {
                    if(logic.login()){
                        userList.add(logic.getUser(), connection);
                        connection.send("УСПЕШНО");
                        Connection.sendAll("Присоединился пользователь: " + logic.getUser().getName());
                        logger.info("Присоединился пользователь: " + logic.getUser().getName());
                        break;
                    }
                }
                if (!logic.chat()){
                    connection.close();
                    userList.remove(logic.getUser());
                    Connection.sendAll(logic.getUser().name + ": Отключился");
                    logger.info(logic.getUser().name + ": Отключился");

                }
            }catch (IOException e){
                e.printStackTrace();

            }
        }
    }
}
