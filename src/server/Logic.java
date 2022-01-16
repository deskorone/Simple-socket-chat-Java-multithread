package server;

import connection.Connection;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;


public class Logic {
    Logger logger = Logger.getLogger(Logic.class);
    User user;
    Connection connection;
    FileManager manager;
    public Logic(Connection connection, FileManager manager) {
        this.connection = connection;
        this.manager = manager;
        this.manager = new FileManager();
    }

    public User getUser() {
        return user;
    }

    public boolean login(){
        try {
            connection.send("Введите имя");
            String name = connection.getMsg();
            connection.send("Введите пароль");
            String password = connection.getMsg();
            User user = new User(name, password);
            this.user = user;
            return manager.chekUserLog(user) && UserList.checkuser(user);
        }catch (IOException e){
            e.printStackTrace();
            return true;
        }
    }

    public boolean chat(){
        logger.info("Пользователь вошел в чат: " + user.getName());
        while (true){
            try {
                String msg = connection.getMsg();
                if (msg != null) {
                    Connection.sendAll(user.getName() + ":  " + msg);
                }
            }catch (IOException e){
                logger.warn("Сокетное соединение разорванно  " + user.getName());
                return false;
            }
        }
    }

}
