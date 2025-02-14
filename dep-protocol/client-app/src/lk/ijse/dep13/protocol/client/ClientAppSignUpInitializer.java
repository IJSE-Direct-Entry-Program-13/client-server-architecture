package lk.ijse.dep13.protocol.client;

import lk.ijse.dep13.protocol.shared.dto.Request;
import lk.ijse.dep13.protocol.shared.dto.Response;
import lk.ijse.dep13.protocol.shared.dto.User;
import lk.ijse.dep13.protocol.shared.util.Command;

import java.io.*;
import java.net.Socket;

public class ClientAppSignUpInitializer {

    public static void main(String[] args) throws Exception {
        String ip = "127.0.0.1";
        int port = 7070;

        Socket socket = new Socket(ip, port);

        OutputStream os = socket.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        User user = new User("nuwan", "kasun@123");
        Request request = new Request(Command.SIGN_UP, user);
        oos.writeObject(request);
        oos.flush();
        System.out.println("Request Sent");

        InputStream is = socket.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        ObjectInputStream ois = new ObjectInputStream(bis);

        if (ois.readObject() instanceof Response response){
            System.out.println(response);
        }else{
            System.out.println("Invalid Response");
        }
        socket.close();
    }
}
