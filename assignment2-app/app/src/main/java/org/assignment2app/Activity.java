package org.assignment2app;

import java.io.*;

public class Activity {
   private static final String imagesDirectoryPath = "app/images/";
   private static final String imageFilename = "life.gif";
   public static void main(String[] args) throws IOException {
       new Activity().onCreate();
    }
   public Activity() {
   }
   public void onCreate() {
      AsyncTask UploadAsyncTask = new UploadAsyncTask(imagesDirectoryPath + imageFilename, imageFilename).execute();
      System.out.println("Waiting for Callback");
      try { 
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         br.readLine();
      } catch (Exception e) { }
   }
}
