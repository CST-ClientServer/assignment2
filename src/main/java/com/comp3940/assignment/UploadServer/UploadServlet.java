package com.comp3940.assignment.UploadServer;

import java.io.*;
import java.time.Clock;

import com.comp3940.assignment.Utils.HtmlExtractor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "UploadServlet", value = "")
public class UploadServlet extends HttpServlet {
   private final HtmlExtractor extractor = new HtmlExtractor();
   protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException, ServletException {
      String html = this.extractor.getHtml(request, "Form.html");
      PrintWriter out = response.getWriter();
      out.println(html);
   }
   protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException, ServletException {
      BufferedReader formData = request.getReader();
      System.out.println(formData);
   }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) {
      try {
         InputStream in = request.getInputStream();   
         ByteArrayOutputStream baos = new ByteArrayOutputStream();  
         byte[] content = new byte[1];
         int bytesRead = -1;      
         while( ( bytesRead = in.read( content ) ) != -1 ) {  
            baos.write( content, 0, bytesRead );  
         }
         Clock clock = Clock.systemDefaultZone();
         long milliSeconds=clock.millis();
         OutputStream outputStream = new FileOutputStream(new File(String.valueOf(milliSeconds) + ".png"));
         baos.writeTo(outputStream);
         outputStream.close();
         PrintWriter out = new PrintWriter(response.getOutputStream(), true);
         File dir = new File(".");
         String[] chld = dir.list();
      	 for(int i = 0; i < chld.length; i++){
            String fileName = chld[i];
            out.println(fileName+"\n");
            System.out.println(fileName);
         }
      } catch(Exception ex) {
         System.err.println(ex);
      }
   }
}