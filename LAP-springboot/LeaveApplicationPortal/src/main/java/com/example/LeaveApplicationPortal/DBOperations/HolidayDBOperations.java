/*package com.example.LeaveApplicationPortal.DBOperations;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class HolidayDBOperations {

    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;
    public HolidayDBOperations() {
        this.mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("lap");
        this.collection = database.getCollection("holidays");
    }

    public List<Document> findAll() {
        List<Document> array = new ArrayList<>();
        for (Document document : collection.find().projection(new Document("_id", 0))) {
            array.add(document);
        }
        return array;
    }
}*/
