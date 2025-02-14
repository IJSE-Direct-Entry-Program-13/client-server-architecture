package lk.ijse.dep13.exec;

import java.io.IOException;

public class AppInitializer {

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] command = {"apt", "install", "vlc"};
        Process process = Runtime.getRuntime().exec(command);
        process.getInputStream().transferTo(System.out);
        //process.waitFor();
        System.out.println("App is about to exit");
    }
}
