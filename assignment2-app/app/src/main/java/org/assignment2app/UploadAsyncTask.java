package org.assignment2app;

public class UploadAsyncTask extends AsyncTask {
   private final String filepath;
   public UploadAsyncTask(String filename) {
      this.filepath = filename;
   }

   protected void onPostExecute(String result) {
      System.out.println(result);
   }

   protected String doInBackground() {
     return new UploadClient().uploadFile(filepath);
   }
}
