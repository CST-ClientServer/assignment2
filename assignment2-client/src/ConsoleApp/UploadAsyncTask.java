package com.comp3940.assignment.ConsoleApp;

public class UploadAsyncTask extends AsyncTask {
   protected void onPostExecute(String result) {
      System.out.println(result);
   }
   protected String doInBackground() {
     return new UploadClient().uploadFile();
   }  
}
