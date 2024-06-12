package com.zhaw.crime_cases;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class DataImporter {

    private static final String DATABASE_NAME = "crime_cases_db";
    private static final String DEFENDANTS_COLLECTION = "defendants";
    private static final String CRIMES_COLLECTION = "crimes";
    private static final String INDICTMENTS_COLLECTION = "indictments";
    private static final String SINGLES_COLLECTION = "singles";
    private static final String MULTIPLES_COLLECTION = "multiples";
    
    private MongoClient mongoClient;
    private MongoDatabase database;
    private RestTemplate restTemplate;

    public DataImporter() {
        mongoClient = MongoClients.create("mongodb+srv://admin:771043.sara@cluster0.5obghvz.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
        database = mongoClient.getDatabase(DATABASE_NAME);
        restTemplate = new RestTemplate();
    }

    public void importData() {
        importDefendants();
        importCrimes();
        importIndictments();
        importSingles();
        importMultiples();
    }

    private void importDefendants() {
        String url = "http://localhost:8080/api/defendants";
        List<Document> defendants = Arrays.asList(restTemplate.getForObject(url, Document[].class));
        MongoCollection<Document> collection = database.getCollection(DEFENDANTS_COLLECTION);
        collection.insertMany(defendants);
    }

    private void importCrimes() {
        String url = "http://localhost:8080/api/crimes";
        List<Document> crimes = Arrays.asList(restTemplate.getForObject(url, Document[].class));
        MongoCollection<Document> collection = database.getCollection(CRIMES_COLLECTION);
        collection.insertMany(crimes);
    }

    private void importIndictments() {
        String url = "http://localhost:8080/api/indictments";
        List<Document> indictments = Arrays.asList(restTemplate.getForObject(url, Document[].class));
        MongoCollection<Document> collection = database.getCollection(INDICTMENTS_COLLECTION);
        collection.insertMany(indictments);
    }

    private void importSingles() {
        String url = "http://localhost:8080/api/singles";
        List<Document> singles = Arrays.asList(restTemplate.getForObject(url, Document[].class));
        MongoCollection<Document> collection = database.getCollection(SINGLES_COLLECTION);
        collection.insertMany(singles);
    }

    private void importMultiples() {
        String url = "http://localhost:8080/api/multiples";
        List<Document> multiples = Arrays.asList(restTemplate.getForObject(url, Document[].class));
        MongoCollection<Document> collection = database.getCollection(MULTIPLES_COLLECTION);
        collection.insertMany(multiples);
    }

    public static void main(String[] args) {
        DataImporter importer = new DataImporter();
        importer.importData();
    }
}
