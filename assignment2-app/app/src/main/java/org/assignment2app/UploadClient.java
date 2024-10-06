package org.assignment2app;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UploadClient {
    public static final int PORT = 8081;
    private static final String caption = "Send Help";
    private static final String date = "2024-04-25";
    private static final String endpoint = "/upload/";
    private static final String host = "localhost";
    private final HttpRequestBuilder builder;

    public UploadClient() {
        builder = new HttpRequestBuilder(host, endpoint);
    }

    public String uploadFile(String filepath, String filename) {
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
            builder.setSocketOut(socket.getOutputStream());
            builder.addTextParameter("caption", caption);
            builder.addTextParameter("date", date);
//            builder.addFileParameter(filename, fileBytes);
            System.out.println("Http Request Successfully Built\n");

            // prepare input stream for server response
            System.out.println("Preparing socket for server response...");
            BufferedReader socketIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            System.out.println("Socket successfully prepared for response\n");

            // send post request
            System.out.println("Sending upload request to server");
            OutputStream socketOut = socket.getOutputStream();
            builder.sendRequest();
//            socketOut.write(builder.getHttpRequest().getBytes(StandardCharsets.UTF_8));
            socketOut.flush();
            System.out.println("File successfully uploaded\n");

            // get response data
            String responseFileNames = "";
            while ((responseFileNames = socketIn.readLine()) != null) {
                listing += responseFileNames;
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