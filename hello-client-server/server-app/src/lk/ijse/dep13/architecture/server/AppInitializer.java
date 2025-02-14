package lk.ijse.dep13.architecture.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class AppInitializer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(5050);
        System.out.println("Server started at port 5050");
        while (true) {
            System.out.println("Waiting for connection...");
            Socket localSocket = serverSocket.accept();
            new Thread(()-> {
                try {
                    System.out.println("Connection accepted");
                    InputStream is = localSocket.getInputStream();
                    while (true){
                        byte[] buffer = new byte[1024];
                        int len = is.read(buffer);
                        System.out.println(new String(buffer,0,len));
                    }
                }catch(Exception e){}
            }).start();
        }

    }
}
