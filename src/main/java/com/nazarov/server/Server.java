package com.nazarov.server;

import java.io.*;
import java.net.*;

public class Server {
    private int portNumber;
    private String webAppPath;
    private String methodName = "";
    private String uri = "";
    private String requestLine = "";

    void setPort(int port) {
        portNumber = port;
    }

    void setWebAppPath(String path) {
        webAppPath = path;
    }

    private void extractUriAndMethodFromRequest() {
        uri = requestLine.substring(requestLine.indexOf(' ') + 1, requestLine.lastIndexOf(' '));
        methodName = requestLine.substring(0, requestLine.indexOf('/'));
    }

    private String response() throws IOException {
        String path = webAppPath + uri;
        File pathToFile = new File(path);
        InputStream inputStream = new FileInputStream(path);
        int fileLength = (int) pathToFile.length();
        byte[] contentArray = new byte[fileLength];
        inputStream.read(contentArray);
        return new String(contentArray);
    }

    private void printRequestInfo(){
        System.out.println("REQUEST LINE IS: " + requestLine);
        System.out.println("METHOD NAME IS: " + methodName);
        System.out.println("uri is: " + uri);
        System.out.println("Request obtained");
        System.out.println("================================================================================================================");
    }

    public void start() throws IOException {

        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(portNumber);
                 Socket socket = serverSocket.accept();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
            ) {
                int lineCounter = 0;
                while (true) {

                    String messageFromClient = bufferedReader.readLine();
                    lineCounter++;
                    System.out.println(messageFromClient);

                    if (lineCounter == 1) {
                        requestLine = messageFromClient;
                    }

                    if (messageFromClient.equals("")) {
                        break;
                    }

                }

                extractUriAndMethodFromRequest();
                printRequestInfo();

                bufferedWriter.write("HTTP/1.1 200 OK");
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                bufferedWriter.write(response());
            }

        }
    }
}
