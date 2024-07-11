/*package com.example.LeaveApplicationPortal.DBOperations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeaveDBOperations {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;


    public LeaveDBOperations() {
        this.mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("lap");
        this.collection = database.getCollection("leave");
    }

    public void save(DBObject formData) {
        Document document = new Document(formData.toMap());
        collection.insertOne(document);
    }

    public List<Document> findAll() {
        List<Document> leave = new ArrayList<>();
        for (Document document : collection.find().projection(new Document("_id", 0))) {
            leave.add(document);
        }
        return leave;
    }

    public List<Document> findByStatus(String status) {
        List<Document> leave = new ArrayList<>();
        DBObject query = new BasicDBObject("status", status);
        for (Document document : collection.find((Bson) query).projection(new Document("_id", 0))) {
            leave.add(document);
        }
        return leave;
    }

    public List<Document> findLeaveByUserid(String userid) {
        List<Document> leave = new ArrayList<>();
        DBObject query = new BasicDBObject("userid", userid);
        for (Document document : collection.find((Bson) query).projection(new Document("_id", 0))) {
            leave.add(document);
        }
        return leave;
    }

    public List<Document> findLeaveByUseridAndStatus(String userid, String status) {
        List<Document> leave = new ArrayList<>();
        DBObject query = new BasicDBObject("userid", userid).append("status", status);
        for (Document document : collection.find((Bson) query).projection(new Document("_id", 0))) {
            leave.add(document);
        }
        return leave;
    }

    public int findByUseridAndLeaveType(String userid, String leaveType) {
        int sum = 0;
        DBObject query = new BasicDBObject("userid", userid).append("leaveType", leaveType).append("status", "Accepted");
        for (Document document : collection.find((Bson) query)) {
            sum += (int) document.get("count");
        }
        return sum;
    }

}*/

