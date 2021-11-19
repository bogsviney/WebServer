package com.nazarov.client;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {


        try (Socket socket = new Socket("localhost", 3000);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
        ) {

            bufferedWriter.write("HELLO, BRO!");
            bufferedWriter.newLine();
            bufferedWriter.flush();

            String echo = bufferedReader.readLine();
            System.out.println(echo);

        }

    }
}
