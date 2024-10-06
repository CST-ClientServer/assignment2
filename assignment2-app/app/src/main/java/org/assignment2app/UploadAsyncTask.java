package org.assignment2app;

public class UploadAsyncTask extends AsyncTask {
   private final String filepath;
   private final String filename;
   public UploadAsyncTask(String filepath, String filename) {
      this.filepath = filepath;
      this.filename = filename;
   }

   protected void onPostExecute(String result) {
      System.out.println(result);
   }

   protected String doInBackground() {
     return new UploadClient().uploadFile(filepath, filename);
   }
}
