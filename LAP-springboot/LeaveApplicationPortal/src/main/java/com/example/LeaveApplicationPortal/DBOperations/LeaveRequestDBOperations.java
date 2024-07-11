/*package com.example.LeaveApplicationPortal.DBOperations;

import com.example.LeaveApplicationPortal.Response.LoginResponse;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LeaveRequestDBOperations {

    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> collection1;

    public LeaveRequestDBOperations() {
        this.mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("lap");
        this.collection = database.getCollection("leaveRequest");
        this.collection1 = database.getCollection("leave");
    }

    public boolean findByUserid(String userid) {
        DBObject query = new BasicDBObject("userid", userid);
        Document document = collection.find((Bson) query).first();
        return document == null;
    }
    public void save(DBObject formData) {
        Document document = new Document(formData.toMap());
        collection.insertOne(document);
    }

    public List<Document> findByApprover(String approver) {
        List<Document> leave = new ArrayList<>();
        DBObject query = new BasicDBObject("approver", approver);
        for (Document document : collection.find((Bson) query).projection(new Document("_id", 0))) {
            leave.add(document);
        }
        return leave;
    }

    public LoginResponse updateStatus(String userid, String newStatus) {
        DBObject query = new BasicDBObject("userid", userid);
        DBObject filter = new BasicDBObject("userid", userid).append("status", "Pending");
        DBObject update = new BasicDBObject("$set", new BasicDBObject("status", newStatus));
        collection1.updateOne((Bson) filter, (Bson) update);
        collection.findOneAndDelete((Bson) query);
        return new LoginResponse("Updated", true);
    }

    public ResponseEntity<byte[]> getFile(String userid) {
        DBObject query = new BasicDBObject("userid", userid);
        for (Document document : collection.find((Bson) query))
            if(document.get("file") != null) {
                Binary image = (Binary) document.get("file");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);
                return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
            }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<Document> findByUser(String userid) {
        List<Document> leave = new ArrayList<>();
        DBObject query = new BasicDBObject("userid", userid);
        for (Document document : collection.find((Bson) query).projection(new Document("_id", 0).append("file", 0)))
            leave.add(document);
        return leave;
    }

    public void update(DBObject formData1) {
        DBObject filter = new BasicDBObject("userid",formData1.get("userid")).append("status", "Pending");
        DBObject update = new BasicDBObject("$set", new BasicDBObject("leaveType", formData1.get("leaveType")).append("startDate", formData1.get("startDate")).append("endDate", formData1.get("endDate")).append("count", formData1.get("count")).append("msg", formData1.get("msg")).append("approver", formData1.get("approver")).append("file", formData1.get("file")));
        collection.updateOne((Bson) filter, (Bson) update);
        collection1.updateOne((Bson) filter, (Bson) update);
    }
}*/
