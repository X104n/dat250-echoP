package VotingApp.db;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MongoDBService {

    private MongoClient mongoClient;
    private MongoDatabase database;

    public MongoDBService(@Value("mongodb://localhost:27017") String connectionString, @Value("feedapp") String dbName) {
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase(dbName);
    }

    public void insertPollResult(String pollResult) {
        MongoCollection<Document> collection = database.getCollection("pollResults");
        Document doc = Document.parse(pollResult);
        collection.insertOne(doc);
    }
}
