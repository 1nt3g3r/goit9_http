package com.http;

import java.io.*;
import java.net.Socket;
import java.util.StringJoiner;

public class RequestHandler implements Runnable {
    private Socket socket;

    private BufferedReader inputReader;

    private String method;
    private String path;
    private String httpVersion;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Parse request
            String line = inputReader.readLine();
            String[] parts = line.split(" ");
            method = parts[0];
            path = parts[1];
            httpVersion = parts[2];

            //Send response
            String response = makeResponse();
            socket.getOutputStream().write(response.getBytes());
            socket.getOutputStream().flush();
            socket.getOutputStream().close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeResponse() {
        StringBuilder builder = new StringBuilder();

        //Content
        String content =
                "<html>" +
                        "<head>" +
                        "</head>" +
                        "" +
                        "<body>" +
                            "<h1>Hello</h1>" +
                        "</body>" +
                "</html>";

        //Append start line
        builder.append(httpVersion).append(" ").append(200).append(" OK\n");

        //Append headers
        builder.append("Content-Length: ").append(content.getBytes().length).append("\n\n");

        //Append content
        builder.append(content);

        return builder.toString();
    }
}
