package org.assignment2app;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpRequestBuilder {
    private static final String separator = "\r\n";
    private static final String boundary = "-------WhatACoolFormBoundaryHaha";
    private final String host;
    private final String endpoint;
    private String httpHeader;
    private String httpBody = "";
    private String httpFilePart;
    private final String httpEnd = "--" + boundary + "--" + separator;
    private byte[] file;
    private long contentLength = 0;
    private OutputStream socketOut;

    public HttpRequestBuilder(String host, String endpoint) {
        this.host = host;
        this.endpoint = endpoint;
    }

    public void setSocketOut(OutputStream socketOut) throws IOException {
        this.socketOut = socketOut;
    }

    public void sendRequest() throws IOException {
        buildHttpHeader();
        socketOut.write(httpHeader.getBytes(StandardCharsets.UTF_8));
        socketOut.write(httpBody.getBytes(StandardCharsets.UTF_8));
        writeFilePart();
        socketOut.write(httpEnd.getBytes(StandardCharsets.UTF_8));
        socketOut.flush();
    }

    public void addTextParameter(String fieldName, String value) throws IOException {
        // build parameter
        StringBuilder builder = new StringBuilder();
        builder.append("--").append(boundary).append(separator);
        builder.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"").append(separator);
        builder.append(separator);
        builder.append(value).append(separator);

        // add to body and add to content length count
        httpBody += builder.toString();
        contentLength += builder.toString().getBytes(StandardCharsets.UTF_8).length;
    }

    public void addFileParameter(String fileName, byte[] file) throws IOException {
        // build request
        StringBuilder builder = new StringBuilder();
        builder.append("--").append(boundary).append(separator);
        builder.append("Content-Disposition: form-data; name=\"File\"; filename=\"").append(fileName).append("\"").append(separator);
        builder.append("Content-Type: application/octet-stream").append(separator);
        builder.append(separator);
        httpFilePart = builder.toString();
        this.file = file;

        // calculate content length
        contentLength += builder.toString().getBytes(StandardCharsets.UTF_8).length +
                separator.getBytes(StandardCharsets.UTF_8).length + file.length;
    }

    private void writeFilePart() throws IOException {
        socketOut.write(httpFilePart.getBytes(StandardCharsets.UTF_8));
        socketOut.write(file);
        socketOut.write(separator.getBytes(StandardCharsets.UTF_8));
    }

    private void buildHttpHeader() {
        // build http header
        StringBuilder builder = new StringBuilder();
        builder.append("POST ").append(endpoint).append(" HTTP/1.1").append(separator);
        builder.append("Host: ").append(host).append(":").append(UploadClient.PORT).append(separator);
        builder.append("Content-Type: multipart/form-data; boundary=").append(boundary).append(separator);
        builder.append("Connection: close").append(separator);

        // calculate length of content-length header
        contentLength += builder.toString().getBytes(StandardCharsets.UTF_8).length;
        contentLength += "Content-Length: ".getBytes(StandardCharsets.UTF_8).length +
                separator.getBytes(StandardCharsets.UTF_8).length +
                String.valueOf(contentLength).getBytes(StandardCharsets.UTF_8).length;
        builder.append("Content-Length: ").append(contentLength).append(separator);
        builder.append(separator);
        httpHeader = builder.toString();
    }
}
