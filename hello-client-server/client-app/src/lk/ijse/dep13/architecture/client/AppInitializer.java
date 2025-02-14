package lk.ijse.dep13.architecture.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class AppInitializer {

    public static void main(String[] args) throws IOException {
        String[] ipAddress = {"127.0.0.1","127.0.0.1"};
        Socket socket = new Socket(ipAddress[new Random().nextInt(2)], 5050);
        OutputStream os = socket.getOutputStream();
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.print("Enter your message: ");
            String message = scanner.nextLine();
            os.write(("Kasun: " + message).getBytes());
            os.flush();
        }
    }
}
