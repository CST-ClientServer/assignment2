package com.comp3940.assignment.UploadServer;

import java.io.*;
public class HttpServletResponse {
   private OutputStream outputStream = null;
   public HttpServletResponse(OutputStream outputStream) {
      this.outputStream = outputStream;
   }
   public OutputStream getOutputStream() {return outputStream;}
}