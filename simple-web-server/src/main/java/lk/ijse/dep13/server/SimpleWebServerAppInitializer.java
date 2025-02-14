package lk.ijse.dep13.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleWebServerAppInitializer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7070);
            System.out.println("Server started on port 7070");
            while (true) {
                Socket socket = serverSocket.accept();
//                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
                new Thread(() -> {
                    try {
                        InputStream is = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);

                        String commandLine = br.readLine();
                        if (commandLine == null) return;
                        String path = commandLine.split(" ")[1];

                        if (path.matches("/customers[/?#]?.*")) {
                            System.out.println(commandLine);

                            /* Read Request Headers*/
                            String line;
                            int contentLength = 0;
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                                if (line.toLowerCase().startsWith("content-length:")) {
                                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                                }
                                if (line.isBlank()) break;
                            }

                            /* Parse Request Payload */
                            int total = 0;
                            while (contentLength != 0) {
                                char[] buffer = new char[1024];
                                int read = br.read(buffer);
                                if (read == -1) break;
                                total += read;
                                System.out.println(new String(buffer, 0, read));
                                if (total == contentLength) break;
                            }

                            String httpResponse = """
                                    HTTP/1.1 200 OK
                                    Content-Type: text/html
                                    
                                    <h1>Customer Information Received</h1>
                                    """;
                            socket.getOutputStream().write(httpResponse.getBytes());
                            socket.close();
                        } else {
                            String httpResponse = """
                                    HTTP/1.1 404 NOT FOUND
                                    Content-Type: text/html
                                    
                                    <h1>404 Not Found</h1>
                                    """;
                            socket.getOutputStream().write(httpResponse.getBytes());
                            socket.close();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
