package org.assignment2app;

import java.io.*;

public class HttpResponseReader {
    private final BufferedReader socketIn;
    private int statusCode;
    public HttpResponseReader(BufferedReader socketIn) {
        this.socketIn = socketIn;
    }

    public int getStatusCode() throws IOException {
        return 69;
    }

    public String[] getListing() {
        return new String[]{"filler"};
    }
}
