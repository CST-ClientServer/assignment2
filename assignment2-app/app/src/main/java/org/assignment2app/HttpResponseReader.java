package org.assignment2app;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpResponseReader {
    private final BufferedReader socketIn;
    private int statusCode = -1;
    public HttpResponseReader(BufferedReader socketIn) {
        this.socketIn = socketIn;
    }

    public int getStatusCode() throws IOException {
        if (statusCode != -1) return statusCode;
        String headerStatus = socketIn.readLine();
        String stringStatusCode = headerStatus.split(" ")[1];
        statusCode = Integer.parseInt(stringStatusCode);
        return statusCode;
    }

    public ArrayList<String> getListing() throws IOException {
        ArrayList<String> listings = new ArrayList<>();
        String buffer;
        while((buffer = socketIn.readLine()) != null) {
            addFilenameToListing(buffer, listings);
        }
        return listings;
    }

    private void addFilenameToListing(String buffer, ArrayList<String> listings) {
        Pattern regexPattern = Pattern.compile("<[^>]*>([^<]*\\b\\w+\\.[^<]+)<\\/[^>]*>");
        Matcher matcher = regexPattern.matcher(buffer);
        if (matcher.find()) {
            listings.add(matcher.group(1));
        }
    }
}