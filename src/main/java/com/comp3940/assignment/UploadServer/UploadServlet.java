package com.comp3940.assignment.UploadServer;

import java.io.*;
import java.time.Clock;
import java.util.Enumeration;

import com.comp3940.assignment.Dao.FormDao;
import com.comp3940.assignment.Utils.ConnectionManager;
import com.comp3940.assignment.Utils.HtmlExtractor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "UploadServlet", value = "")
@MultipartConfig
public class UploadServlet extends HttpServlet {
   private final HtmlExtractor extractor = new HtmlExtractor();
   private FormDao formDao;
   public void init() {
      formDao = new FormDao();
   }
   protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException, ServletException {
      String html = this.extractor.getHtml(request, "Form");
      PrintWriter out = response.getWriter();
      out.println(html);
   }
   protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws IOException, ServletException {
      try {
         String caption = request.getParameter("caption");
         String date = request.getParameter("date");
         Part file = request.getPart("file");
         formDao.insertFormData(caption, date, file);

      } catch (Exception e) {
         System.err.println(e.getMessage());
      }
   }
}