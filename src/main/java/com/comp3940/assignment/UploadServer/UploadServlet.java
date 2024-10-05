package com.comp3940.assignment.UploadServer;

import java.io.*;
import java.time.Clock;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.lang.reflect.*;

//import com.comp3940.assignment.Dao.FormDao;
import com.comp3940.assignment.Exceptions.FileTooLargeException;
//import com.comp3940.assignment.Utils.ConnectionManager;
import com.comp3940.assignment.Utils.FileSystemHandler;
import com.comp3940.assignment.Utils.HtmlExtractor;
import com.comp3940.assignment.Utils.JavaReflectTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "UploadServlet", value = "")
@MultipartConfig
public class UploadServlet extends HttpServlet {
   private static final long MAX_FILE_SIZE = 16 * 1024 * 1024;
   private final HtmlExtractor extractor = new HtmlExtractor();
   private ObjectMapper mapper = new ObjectMapper();
//   private FormDao formDao;
   public void init() {
//      formDao = new FormDao();
      JavaReflectTest.log("com.comp3940.assignment.UploadServer.UploadServlet");
   }
   protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException, ServletException {
      System.out.println("do get");
      String html = this.extractor.getHtml(request, "Form");

      PrintWriter out = response.getWriter();
      out.println(html);
   }

   protected void doOptions(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws ServletException, IOException {
      System.out.println("??????? do option called??????\n");
      response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
      response.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,PATCH,OPTIONS");
      response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
      response.setStatus(HttpServletResponse.SC_OK);
   }

   protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException, ServletException {
      System.out.println("do post called");
      try {
         response.setHeader("Access-Control-Allow-Origin", "*");
         response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
         response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
         response.setStatus(HttpServletResponse.SC_OK);
         System.out.println("??????? in do Post??????\n");

         String caption = request.getParameter("caption") != null ? request.getParameter("caption") : "";
         String date = request.getParameter("date") != null ? request.getParameter("date") : "";
         Part file = request.getPart("file");

         if (file.getSize() >= MAX_FILE_SIZE) throw new FileTooLargeException("File is greater than " + MAX_FILE_SIZE);
//         formDao.insertFormData(caption, date, file);
         FileSystemHandler.saveToUploads(caption, date, file, request);
         sendListing(request, response);

      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }

   private void sendListing(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException {
      File dir = new File(request.getServletContext().getRealPath("/uploads"));
      String[] dirFiles = dir.list();
      Arrays.sort(dirFiles);
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      String html = extractor.getHtml(request, "Directory");

      // option 1: send html directly to front end
      String populatedHtml = addDirectoryToHtml(html, dirFiles);
      response.setContentType("text/html");
      response.getWriter().println(populatedHtml);

      // option 2: send json to front end, and let front end deal with populating
//      String json = mapper.writeValueAsString(dirFiles);
//      response.getWriter().write(json);
   }

   private String addDirectoryToHtml(String html, String[] directoryList) {
      StringBuilder htmlDirFiles = new StringBuilder();
      for (String file : directoryList) {
         htmlDirFiles.append("<h2>").append(file).append("</h2><br>\n");
      }
      return html.replace("directoryHere", htmlDirFiles);
   }
}