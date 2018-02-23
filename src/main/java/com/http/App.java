package com.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;


public class App {
    public static final int PORT = 8080;

    public static void main( String[] args ) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        while(true) {
            Socket socket = serverSocket.accept();

            RequestHandler handler = new RequestHandler(socket);

            new Thread(handler).start();
        }
    }
}
