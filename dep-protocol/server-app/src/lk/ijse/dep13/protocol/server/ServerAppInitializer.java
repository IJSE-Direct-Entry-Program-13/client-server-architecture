package lk.ijse.dep13.protocol.server;

import lk.ijse.dep13.protocol.shared.dto.Request;
import lk.ijse.dep13.protocol.shared.dto.Response;
import lk.ijse.dep13.protocol.shared.dto.User;
import lk.ijse.dep13.protocol.shared.util.Status;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerAppInitializer {
    public static void main(String[] args) throws IOException {
        final ArrayList<User> USERS = new ArrayList<>();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(7070);
        }catch (BindException exp){
            System.out.println("Port 7070 is already in use, trying with another port");
            serverSocket = new ServerSocket(0);
        }
        System.out.println("Server started on port " + serverSocket.getLocalPort());
        while (true) {
            System.out.println("Waiting for connection...");
            Socket localSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + localSocket.getRemoteSocketAddress());
            new Thread(()->{
                try {
                    InputStream is = localSocket.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    ObjectInputStream ois = new ObjectInputStream(bis);

                    OutputStream os = localSocket.getOutputStream();
                    BufferedOutputStream bos = new BufferedOutputStream(os);
                    ObjectOutputStream oos = new ObjectOutputStream(bos);

                    if (ois.readObject() instanceof Request request){
                        User user = request.user();
                        if (user == null){
                            oos.writeObject(new Response(Status.ERROR,"User can't be null"));
                        }else if (user.username().isBlank() || user.password().isBlank()){
                            oos.writeObject(new Response(Status.ERROR,"Username or password can't be blank"));
                        }else if (user.username().strip().length() <= 3){
                            oos.writeObject(new Response(Status.ERROR,"Invalid username, username is too short"));
                        }else if (user.password().strip().length() <= 6){
                            oos.writeObject(new Response(Status.ERROR,"Invalid password, password is too short"));
                        }else {
                            switch (request.command()) {
                                case SIGN_UP -> {
                                    boolean userExists = false;
                                    for (User u : USERS) {
                                        if (u.username().equals(user.username().strip())){
                                            userExists = true;
                                            break;
                                        }
                                    }
                                    if (!userExists){
                                        USERS.add(user);
                                        oos.writeObject(new Response(Status.OK, "User account created successfully"));
                                    }else{
                                        oos.writeObject(new Response(Status.ERROR,"Username is already in use"));
                                    }
                                }
                                case SIGN_IN -> {
                                    boolean userExists = false;
                                    for (User u : USERS) {
                                        if (u.username().equals(user.username().strip())
                                                && u.password().equals(user.password().strip())){
                                            userExists = true;
                                            break;
                                        }
                                    }
                                    if (!userExists){
                                        oos.writeObject(new Response(Status.ERROR,"Invalid login credentials"));
                                    }else{
                                        oos.writeObject(new Response(Status.OK,"Successfully logged in"));
                                    }
                                }
                                case null, default -> oos.writeObject(new Response(Status.ERROR,"Invalid command"));
                            }
                        }
                    }else{
                        oos.writeObject(new Response(Status.ERROR, "Invalid Request"));
                    }
                    oos.flush();
                } catch (EOFException e) {
                    System.out.println("Connection lost");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
