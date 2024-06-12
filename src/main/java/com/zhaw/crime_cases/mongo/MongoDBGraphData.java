package com.zhaw.crime_cases.mongo;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MongoDBGraphData {
    private static final String CONNECTION_STRING = "mongodb+srv://admin:771043.sara@cluster0.5obghvz.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    private static final String DATABASE_NAME = "crime_cases_db";
    private static final String DEFENDANTS_COLLECTION = "defendants";
    private static final String INDICTMENTS_COLLECTION = "indictments";
    private static final String NODES_OUTPUT_FILE = "src/main/resources/output/nodes_output.csv";
    private static final String EDGES_OUTPUT_FILE = "src/main/resources/output/edges_output.csv";

    public static void main(String[] args) {
        try (var mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            Set<String> nodeIds = new HashSet<>();
            Set<String> edgePairs = new HashSet<>();

            MongoCollection<Document> defendantsCollection = database.getCollection(DEFENDANTS_COLLECTION);
            MongoCollection<Document> indictmentsCollection = database.getCollection(INDICTMENTS_COLLECTION);

            // Extract nodes
            for (Document doc : defendantsCollection.find()) {
                String id = doc.get("_id").toString();
                String name = doc.getString("name");
                nodeIds.add(id + "," + name);
            }

            // Extract edges
            for (Document doc : indictmentsCollection.find()) {
                String defendantId = doc.get("defendantId").toString();
                for (Document otherDoc : indictmentsCollection.find()) {
                    if (!doc.equals(otherDoc) && defendantId.equals(otherDoc.get("defendantId").toString())) {
                        String otherId = otherDoc.get("defendantId").toString();
                        edgePairs.add(defendantId + "," + otherId);
                    }
                }
            }

            // Write nodes to CSV
            try (FileWriter writer = new FileWriter(NODES_OUTPUT_FILE)) {
                writer.write("ID,Name\n");
                for (String node : nodeIds) {
                    writer.write(node + "\n");
                }
            }

            // Write edges to CSV
            try (FileWriter writer = new FileWriter(EDGES_OUTPUT_FILE)) {
                writer.write("From,To\n");
                for (String edge : edgePairs) {
                    writer.write(edge + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
