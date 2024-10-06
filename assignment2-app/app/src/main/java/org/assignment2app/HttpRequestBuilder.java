package org.assignment2app;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpRequestBuilder {

    private static final String separator = "\r\n";
    private static final String boundary = "boundary";
    private final String host;
    private final String endpoint;
    private final String httpHeader;
    private OutputStream socketOut;

    public HttpRequestBuilder(String host, String endpoint) {
        this.host = host;
        this.endpoint = endpoint;
        httpHeader = buildHttpHeader();
    }

    public void setSocketOut(OutputStream socketOut) throws IOException {
        this.socketOut = socketOut;
        socketOut.write(httpHeader.getBytes(StandardCharsets.UTF_8));
    }

    public void sendRequest() throws IOException {
        socketOut.write(getHttpEnd().getBytes(StandardCharsets.UTF_8));
        socketOut.flush();
    }

    public void addTextParameter(String fieldName, String value) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("--").append(boundary).append(separator);
        builder.append("Content-Disposition: form-data; name=\"").append(fieldName).append("\"").append(separator);
        builder.append(separator);
        builder.append(value).append(separator);
        socketOut.write(builder.toString().getBytes(StandardCharsets.UTF_8));
    }

    public void addFileParameter(String fileName, byte[] file) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("--").append(boundary).append(separator);
        builder.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(fileName).append("\"").append(separator);
        builder.append("Content-Type: application/octet-stream").append(separator);
        builder.append(separator);
        socketOut.write(builder.toString().getBytes(StandardCharsets.UTF_8));
        socketOut.write(file);
        socketOut.write(separator.getBytes(StandardCharsets.UTF_8));
    }

    private String getHttpEnd() {
        return "--" + boundary + "--" + separator;
    }

    private String buildHttpHeader() {
        StringBuilder builder = new StringBuilder();
        builder.append("POST ").append(endpoint).append(" HTTP/1.1").append(separator);
        builder.append("Host: ").append(host).append(UploadClient.PORT).append(separator);
        builder.append("Content-Type: multipart/form-data; boundary=").append(boundary).append(separator);
        builder.append("Connection: close").append(separator);
        builder.append(separator);
        return builder.toString();
    }
}
