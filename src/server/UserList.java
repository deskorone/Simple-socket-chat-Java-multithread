package server;

import connection.Connection;

import java.util.HashMap;
import java.util.Map;

public class UserList {
    private static Map<User, Connection> userList = new HashMap<>();

    public synchronized void add(User user, Connection connection){
        userList.put(user,connection);
    }

    public synchronized void remove(User user){
        userList.remove(user);
    }

    public synchronized static Map<User, Connection> getUserList() {
        return userList;
    }

    public static synchronized boolean checkuser(User user){
        for (User i : userList.keySet()){
            if(user.getName().equals(i.getName())){
                return false;
            }
        }
        return true;
    }

}
