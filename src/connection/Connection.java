package connection;

import server.UserList;

import java.io.*;
import java.lang.invoke.StringConcatFactory;
import java.net.Socket;

public class Connection implements Closeable{
    Socket socket;
    BufferedWriter out = null;
    BufferedReader in = null;


    public Connection(Socket socket) throws IOException {
        this.socket = new Socket();
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String msg) throws IOException{
        synchronized (out){
                out.write(msg + "\n");
                out.flush();
        }
    }

    public String getMsg() throws IOException{
        synchronized (in){
                String msg = in.readLine();
                return msg;
        }
    }

    public String getInfo(){
        return "Client connected:  " + this.socket.getPort() + "\n";
    }


    @Override
    public void close() throws IOException {
        in.close();
        out.close();
    }

    public static synchronized void sendAll(String msg) throws IOException {
        for (Connection i : UserList.getUserList().values()) {
            i.send(msg);
        }
    }
}
