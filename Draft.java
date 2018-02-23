package com.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);

        while(true) {
            final Socket socket = serverSocket.accept();

            new Thread() {
                @Override
                public void run() {
                    try {
                        handleRequest(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }

    private static void handleRequest(Socket socket) throws IOException {
        //Read HTTP Input
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String line = null;
        while((line = reader.readLine()) != null) {
            if (line.length() <= 0) {
                break;
            } else {
                System.out.println(line);
            }
        }

        //Write HTTP Response
        OutputStream outputStream = socket.getOutputStream();

        String content = "<h1>Hello World</h1>";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("HTTP/1.1 200 OK\r\n");
        stringBuilder.append("Connection: close\r\n");
        stringBuilder.append("Content-Length: " + content.length()).append("\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append(content);

        byte[] response = stringBuilder.toString().getBytes();
        outputStream.write(response);

        outputStream.flush();
    }
}
