package com.comp3940.assignment.Utils;

import jakarta.servlet.http.Part;

import java.io.*;

public class FileSystemHandler {
    public static void saveToUploads(String caption, String date, Part filePart) throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream = filePart.getInputStream();

    }

    private String combineName(String caption, String date, Part filePart) {
        return  caption + "+" + date + "+" + filePart.getSubmittedFileName();
    }
}
