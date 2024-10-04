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
    public static String formDatabase = "FormData";
    private static ConnectionManager INSTANCE = null;
    private final DBConnection connection = new DBConnection();
    private final ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
    private final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connection.getConnectionString()))
            .serverApi(serverApi)
            .build();
    private MongoClient mongoClient;

    public static ConnectionManager getInstance() {
        if (INSTANCE != null) return INSTANCE;
        INSTANCE = new ConnectionManager();
        return INSTANCE;
    }
    private ConnectionManager() {
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                this.mongoClient = mongoClient;
                System.out.println("Connection established.");
            } catch (MongoException e) {
                // close server if no connection to database
                System.err.println("Unable to connect to MongoDB, shutting down server.");
                System.exit(1);
            }
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
}
