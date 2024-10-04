package com.comp3940.assignment.Dao;

import com.comp3940.assignment.Utils.ConnectionManager;
import com.mongodb.client.*;
import com.mongodb.client.result.InsertOneResult;
import jakarta.servlet.http.Part;
import org.bson.Document;

public class FormDao {
    private final ConnectionManager connection = ConnectionManager.getInstance();
    public void insertFormData(String caption, String date, Part file) {
        MongoDatabase db = connection.getDB(ConnectionManager.databaseName);
        MongoCollection<Document> collection = db.getCollection("Form");
        Document entry = new Document("caption", caption).append("date", date).append("filename", file.getSubmittedFileName());
        InsertOneResult result = collection.insertOne(entry);
        if (result.wasAcknowledged()) System.out.println("Successfully inserted: " + result);
        else System.out.println("Failed to insert");
    }
}
