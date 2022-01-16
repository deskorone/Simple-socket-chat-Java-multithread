package server;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class FileManager {
    private ArrayList<User> userList = new ArrayList<>();
    private File file;

    public  FileManager(){
        file = new File("src/server/users.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            while(reader.ready()){
                User user = new User(reader.readLine() , reader.readLine());
                userList.add(user);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized boolean chekUserLog(User user) {
        for(User i : userList){
            if(i.getName().equals(user.getName()) && i.getPassword().equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }

}
