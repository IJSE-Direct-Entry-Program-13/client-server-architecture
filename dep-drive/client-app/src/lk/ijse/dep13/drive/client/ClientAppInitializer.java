package lk.ijse.dep13.drive.client;

import java.io.*;
import java.net.Socket;

public class ClientAppInitializer {

    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 6060;
        String username = "Kasun";
        File file = new File("/home/ranjith-suranga/Pictures/Wallpapers/1.jpg");
        if (!file.exists()){
            System.out.println("File not found");
            return;
        }

        Socket socket = null;
        try {
            socket = new Socket(host, port);
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(username);
            bw.newLine();
            bw.write(file.getName());
            bw.newLine();
            bw.flush();

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            BufferedOutputStream bos = new BufferedOutputStream(os);

            System.out.println("Start: File uploading");
            while (true){
                byte[] buffer = new byte[1024];
                int len = bis.read(buffer);
                if (len == -1){
                    break;
                }
                bos.write(buffer, 0, len);
            }
            bos.flush();
            System.out.println("Finish: File uploading");
            bis.close();
        } catch (IOException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }finally{
            socket.close();
        }
    }
}
