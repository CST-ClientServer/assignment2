package org.assignment2app;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class UploadClient {
    private static final HashMap<Integer, String> settings = new HashMap<>();
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
        String returnString = "";
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
            HttpResponseReader reader = new HttpResponseReader(socketIn);
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
            System.out.println("Request successfully sent\n");

            // get response data
            System.out.println("Waiting for server response...");
            if (reader.getStatusCode() != 200) {
                throw new WrongStatusException("Server returned a status code other than 200: " + reader.getStatusCode());
            }
            System.out.println("Server returned 200 status\n");

            // create listing string
            StringBuilder stringBuilder = new StringBuilder().append("Current files in server\n").append("\n");
            for (String listing : reader.getListing()) {
                stringBuilder.append(listing).append("\n");
            }
            returnString = stringBuilder.toString();

            // close socket
            socket.close();

        } catch (WrongStatusException e) {
            System.err.println("Wrong Status Exception: " + e.getMessage());
        } catch (Exception e) {
            // print error message
            System.err.println("Process Aborted: " + e.getMessage());
        }
        return returnString;
    }
}