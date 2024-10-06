package org.assignment2app;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class UploadClient {
    private static final HashMap<Integer, String> settings = new HashMap<>() {{
        put(8081, "/upload/upload"); put(8082, "/assignment_war/");
    }};
    static {
        settings.put(8081, "/upload/upload");
        settings.put(8082, "/assignment_war/");
    }
    private static final String caption = "Send Help";
    private static final String date = "2024-04-25";
    private static final String host = "localhost";
    private final HttpRequestBuilder builder;
    public static final int PORT = 8082;

    public UploadClient() {
        builder = new HttpRequestBuilder(host, settings.get(PORT));
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

            // prepare input stream for server response
            System.out.println("Preparing socket for server response...");
            BufferedReader socketIn = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            System.out.println("Socket successfully prepared for response\n");

            // build http request
            System.out.println("Building Http Request");
            builder.setSocketOut(socket.getOutputStream());
            builder.addTextParameter("caption", caption);
            builder.addTextParameter("date", date);
            builder.addFileParameter(filename, fileBytes);
            System.out.println("Http Request Successfully Built\n");

            // send post request
            System.out.println("Sending upload request to server");
            builder.sendRequest();
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