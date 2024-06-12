package com.zhaw.crime_cases.mongo;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MongoDBGraphData {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://admin:771043.sara@cluster0.5obghvz.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (var mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("crime_cases_db");
            MongoCollection<Document> indictmentCollection = database.getCollection("indictments");
            MongoCollection<Document> defendantCollection = database.getCollection("defendants");

            // Überprüfen, ob Daten in den Sammlungen vorhanden sind
            long indictmentCount = indictmentCollection.countDocuments();
            long defendantCount = defendantCollection.countDocuments();

            System.out.println("Anzahl der Indictments: " + indictmentCount);
            System.out.println("Anzahl der Defendants: " + defendantCount);

            if (indictmentCount == 0 || defendantCount == 0) {
                System.out.println("Keine Daten in den Sammlungen gefunden.");
                return;
            }

            Map<Integer, String> defendantMap = new HashMap<>();
            for (Document defendant : defendantCollection.find()) {
                Integer id = defendant.getInteger("id");
                String name = defendant.getString("name");
                defendantMap.put(id, name);
            }

            try (FileWriter nodesWriter = new FileWriter("src/main/resources/output/nodes_output.csv");
                 FileWriter edgesWriter = new FileWriter("src/main/resources/output/edges_output.csv")) {

                nodesWriter.append("ID,Name\n");
                edgesWriter.append("From,To\n");

                for (Document indictment : indictmentCollection.find()) {
                    Integer defendantId = indictment.getInteger("defendantId");
                    Integer crimeId = indictment.getInteger("crimeId");

                    // Überprüfen, ob die IDs nicht null sind
                    if (defendantId == null || crimeId == null) {
                        continue;
                    }

                    // Add nodes for defendants
                    if (defendantMap.containsKey(defendantId)) {
                        nodesWriter.append(defendantId.toString()).append(",").append(defendantMap.get(defendantId)).append("\n");
                    }

                    // Add edge
                    edgesWriter.append(defendantId.toString()).append(",").append(crimeId.toString()).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
