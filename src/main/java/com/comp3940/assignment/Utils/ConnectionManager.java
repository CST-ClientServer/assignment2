package com.comp3940.assignment.Utils;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ConnectionManager {
    public static String databaseName = "Assignment2";
    private static ConnectionManager INSTANCE = null;
    private MongoClient mongoClient;

    public static ConnectionManager getInstance() {
        if (INSTANCE != null) return INSTANCE;
        INSTANCE = new ConnectionManager();
        return INSTANCE;
    }
    private ConnectionManager() {
        DBConnection connection = new DBConnection();
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connection.getConnectionString()))
                .serverApi(serverApi)
                .build();
        try {
            mongoClient = MongoClients.create(settings);
            // Send a ping to confirm a successful connection
            MongoDatabase database = mongoClient.getDatabase("admin");
            database.runCommand(new Document("ping", 1));
            System.out.println("Connection established.");
        } catch (MongoException e) {
            // close server if no connection to database
            System.err.println("Unable to connect to MongoDB, shutting down server.");
            System.exit(1);
        }

    }
    public MongoDatabase getDB(String database) {
        try {
            return mongoClient.getDatabase(database);
        } catch (MongoException e) {
            System.err.println(database + " does not exist");
            return null;
        }
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
