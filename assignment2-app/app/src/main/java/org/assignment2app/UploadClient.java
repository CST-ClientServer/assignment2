package org.assignment2app;

import java.io.*;
import java.net.*;

public class UploadClient {
    private static final int PORT = 8081;
    private final HttpBuilder builder;

    public UploadClient() {
        builder = new HttpBuilder();
    }

    public String uploadFile(String filepath) {
        String listing = "";
        try {
            // connect socket
            System.out.println("Connecting socket to localhost...");
            Socket socket = new Socket("localhost", PORT);
            System.out.println("Connection established\n");

            // extract file bytes
            System.out.println("Reading file bytes..");
            FileInputStream fileInputStream = new FileInputStream(filepath);
            byte[] fileBytes = fileInputStream.readAllBytes();
            fileInputStream.close();
            System.out.println("File reading complete\n");

            // build http request
            System.out.println("Building Http Request");
            // build http request here
            System.out.println("Http Request Successfully Built");

            // prepare input stream for server response
            System.out.println("Preparing socket for server response...");
            BufferedReader socketIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            System.out.println("Socket successfully prepared for response\n");

            // send post request
            System.out.println("Sending upload request to server");
            // post stuff here
            System.out.println("File successfully uploaded\n");

            // get response data
            String filename = "";
            while ((filename = socketIn.readLine()) != null) {
                listing += filename;
            }

            // close socket
            socket.close();
        } catch (Exception e) {
            // print error message
            System.err.println("Process Aborted: " + e.getMessage());
        }
        return listing;
    }
}