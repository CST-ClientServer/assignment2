package com.comp3940.assignment.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.*;
import java.time.Clock;

public class FileSystemHandler {
    private static final int READ_CHUNK = 8192; // 8kB buffer
    public static void saveToUploads(String caption, String date, Part filePart, HttpServletRequest request) {
        try {
            InputStream inputStream = filePart.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] content = new byte[READ_CHUNK];
            int bytesRead;
            while( ( bytesRead = inputStream.read( content ) ) != -1 ) {
                baos.write( content, 0, bytesRead );
            }
            OutputStream outputStream = new FileOutputStream(createName(caption, date, filePart, request));
            baos.writeTo(outputStream);
            outputStream.close();
            baos.close();

        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static String createName(String caption, String date, Part filePart, HttpServletRequest request) {
        // calculate time
        Clock clock = Clock.systemDefaultZone();
        long milliseconds = clock.millis();

        // get filepath to uploads
        String filepath = request.getServletContext().getRealPath("/uploads/");
        return filepath + caption + "+" + date + "-" + milliseconds + "+" + filePart.getSubmittedFileName();
    }
}
