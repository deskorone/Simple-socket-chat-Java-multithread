package client;

import connection.Connection;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    Socket socket;
    Connection connection;
    Scanner scanner;

    public Client() throws IOException {
        scanner = new Scanner(System.in);
        this.socket = new Socket("localhost", 9090);
        try (Connection connection = new Connection(socket)) {
            String line;
            while (true) {
                line = connection.getMsg();
                if (line != null) {
                    System.out.println(line);
                }
                if (line.equals("УСПЕШНО")) {
                    break;
                }
                String msg = scanner.nextLine();
                connection.send(msg);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String msg = connection.getMsg();
                            if (msg != null) {
                                System.out.println(msg);
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            boolean flag= true;
            while (flag) {
                String m = scanner.nextLine();
                connection.send(m);
            }
        }
    }

    public void chat(){
        try {
            String msg = connection.getMsg();
            while (msg != null) {
                System.out.println(connection);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String inputString(){
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;

    }

}
