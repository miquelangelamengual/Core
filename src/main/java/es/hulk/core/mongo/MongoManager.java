package es.hulk.core.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import es.hulk.core.Core;
import lombok.Getter;
import org.bson.Document;

import java.util.Collections;

@Getter
public class MongoManager {

    private final Core core;

    private MongoClient mongoClient;

    private final MongoDatabase mongoDatabase;
    private final MongoCollection<Document> ranks;
    private final MongoCollection<Document> profiles;

    @SuppressWarnings("all")
    public MongoManager(Core core) {
        this.core = core;

        try {
            if (core.getSettingsConfig().getConfig().getBoolean("MONGO.URI.ENABLED")) {
                this.mongoClient = new MongoClient(new MongoClientURI(core.getSettingsConfig().getConfig().getString("MONGO.URI.URL")));
            } else {
                String hostname = core.getSettingsConfig().getConfig().getString("MONGO.HOST");
                int port = core.getSettingsConfig().getConfig().getInt("MONGO.PORT");
                if (core.getSettingsConfig().getConfig().getBoolean("MONGO.AUTHENTICATION.ENABLED")) {
                    this.mongoClient = new MongoClient(new ServerAddress(hostname, port), Collections.singletonList(
                            MongoCredential.createCredential(
                                    core.getSettingsConfig().getConfig().getString("MONGO.AUTHENTICATION.USERNAME"),
                                    core.getSettingsConfig().getConfig().getString("MONGO.AUTHENTICATION.AUTHENTICATION-DATABASE"),
                                    core.getSettingsConfig().getConfig().getString("MONGO.AUTHENTICATION.PASSWORD").toCharArray())));
                } else {
                    this.mongoClient = new MongoClient(new ServerAddress(hostname, port));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.mongoDatabase = mongoClient.getDatabase(core.getSettingsConfig().getConfig().getString("MONGO.DATABASE"));
        this.createCollections();

        this.ranks = mongoDatabase.getCollection("ranks");
        this.profiles = mongoDatabase.getCollection("profiles");
    }

    public void createCollections() {
        if (checkCollection("ranks")) return;
        if (checkCollection("profiles")) return;

        mongoDatabase.createCollection("profiles");
        mongoDatabase.createCollection("ranks");
    }

    public boolean checkCollection(String name) {
        MongoIterable<String> collection = this.mongoDatabase.listCollectionNames();
        for(String s : collection) {
            if(s.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
