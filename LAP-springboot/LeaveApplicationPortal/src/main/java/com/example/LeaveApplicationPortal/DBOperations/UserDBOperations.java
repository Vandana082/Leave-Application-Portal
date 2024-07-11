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
public class UserDBOperations {

    private final MongoClient mongoClient;
    private MongoCollection<Document> collection;
    public UserDBOperations() {
        this.mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("lap");
        this.collection = database.getCollection("user");
    }

    public boolean findByUserid(String userid) {
        DBObject query = new BasicDBObject("userid", userid);
        Document document = collection.find((Bson) query).first();
        return document != null;
    }

    public void save(DBObject formData) {
        Document document = new Document(formData.toMap());
        collection.insertOne(document);
    }

    public boolean findOneByUseridAndPassword(String userid, String password) {
        DBObject query = new BasicDBObject("userid", userid).append("password", password);
        Document document = collection.find((Bson) query).first();
        return document != null;
    }

   public boolean findByCategory(String userid) {
       DBObject query = new BasicDBObject("userid", userid).append("category", "Management");
       Document document = collection.find((Bson) query).first();
       return document != null;
   }

    public Document findUser(String userid) {
        DBObject query = new BasicDBObject("userid", userid);
        return collection.find((Bson) query).projection(new Document("_id", 0)).first();
    }

    public void changePassword(String userid, String newPwd) {
        DBObject filter = new BasicDBObject("userid", userid);
        DBObject update = new BasicDBObject("$set", new BasicDBObject("password", newPwd));
        collection.updateOne((Bson) filter, (Bson) update);
    }

    public boolean findOneByUseridAndEmail(String userid, String email) {
        DBObject query = new BasicDBObject("userid", userid).append("email", email);
        Document document = collection.find((Bson) query).first();
        return document != null;
    }

    public List<String> findUsernameByCategoryManagement() {
        List<String> usernames = new ArrayList<>();
        DBObject query = new BasicDBObject("category", "Management");
        for (Document document : collection.find((Bson) query).projection(new Document("username", 1).append("_id", 0))) {
            usernames.add(document.getString("username"));
        }
        return usernames;
    }

    public List<Document> findAll() {
        List<Document> user = new ArrayList<>();
        for (Document document : collection.find().projection(new Document("_id", 0).append("password", 0).append("_class", 0))) {
            user.add(document);
        }
        return user;
    }

    public void deleteUser(String userid) {
        Document filter = new Document("userid", userid);
        collection.deleteOne(filter);
    }
}*/
