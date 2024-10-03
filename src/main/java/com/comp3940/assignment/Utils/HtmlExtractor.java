package com.comp3940.assignment.Utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlExtractor {
    public String getHtml(HttpServletRequest request, String htmlPage) throws IOException {
        String filepath = request.getServletContext().getRealPath("/pages/" + htmlPage);
        return new String(Files.readAllBytes(Paths.get(filepath)));
    }
}
