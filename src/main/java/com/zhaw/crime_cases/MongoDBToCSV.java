package com.zhaw.crime_cases;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;

public class MongoDBToCSV {

    private static final String DATABASE_NAME = "crime_cases_db";
    private static final String DEFENDANTS_COLLECTION = "defendants";
    private static final String CRIMES_COLLECTION = "crimes";
    private static final String INDICTMENTS_COLLECTION = "indictments";
    private static final String SINGLES_COLLECTION = "singles";
    private static final String MULTIPLES_COLLECTION = "multiples";

    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoDBToCSV() {
        mongoClient = MongoClients.create("mongodb+srv://admin:771043.sara@cluster0.5obghvz.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
        database = mongoClient.getDatabase(DATABASE_NAME);
    }

    public void exportData() {
        exportCollectionToCSV(DEFENDANTS_COLLECTION, "defendants_output.csv");
        exportCollectionToCSV(CRIMES_COLLECTION, "crimes_output.csv");
        exportCollectionToCSV(INDICTMENTS_COLLECTION, "indictments_output.csv");
        exportCollectionToCSV(SINGLES_COLLECTION, "singles_output.csv");
        exportCollectionToCSV(MULTIPLES_COLLECTION, "multiples_output.csv");
    }

    private void exportCollectionToCSV(String collectionName, String fileName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        try (MongoCursor<Document> cursor = collection.find().iterator();
             FileWriter csvWriter = new FileWriter(fileName)) {
            
            boolean headerWritten = false;
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                if (!headerWritten) {
                    csvWriter.append(String.join(",", doc.keySet()));
                    csvWriter.append("\n");
                    headerWritten = true;
                }
                csvWriter.append(String.join(",", doc.values().stream().map(Object::toString).toArray(String[]::new)));
                csvWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MongoDBToCSV exporter = new MongoDBToCSV();
        exporter.exportData();
    }
}
