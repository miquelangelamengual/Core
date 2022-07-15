package es.hulk.core.rank;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import es.hulk.core.Core;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

@Getter
@Setter
public class Rank {

    @Getter
    private static final Map<Rank, String> ranks = Maps.newHashMap();

    private String name, prefix, suffix;
    private int priority;
    private String color;
    private boolean defaultRank;
    private List<String> permissions, inheritances;

    public Rank(String name) {
        this.name = name;

        ranks.put(this, name);
    }

    @ConstructorProperties({
            "name",
            "prefix",
            "suffix",
            "priority",
            "color",
            "defaultRank",
            "permissions",
            "inheritances"})
    public Rank(
            String name,
            String prefix,
            String suffix,
            int priority,
            String color,
            boolean defaultRank,
            List<String> permissions,
            List<String> inheritances
    ) {
        this(name);
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.priority = priority;
        this.color = color;
        this.defaultRank = defaultRank;
        this.permissions = permissions;
        this.inheritances = inheritances;
    }

    public String toJSON() {
        JSONObject object = new JSONObject();

        object.put("name", name);
        object.put("prefix", prefix);
        object.put("suffix", suffix);
        object.put("priority", priority);
        object.put("color", color);
        object.put("defaultRank", defaultRank);
        object.put("permissions", permissions);
        object.put("inheritances", inheritances);

        return object.toString();
    }

    public static Rank fromJSON(String json) {
        JSONObject object = new JSONObject(json);

        List<String> permissions = object.getJSONArray("permissions").toList().stream().map(String::valueOf).collect(Collectors.toList());
        List<String> inheritances = object.getJSONArray("inheritances").toList().stream().map(String::valueOf).collect(Collectors.toList());

        return new Rank(
                object.getString("name"),
                object.getString("prefix"),
                object.getString("suffix"),
                object.getInt("priority"),
                object.getString("color"),
                object.getBoolean("defaultRank"),
                permissions,
                inheritances
        );
    }

    public static void init() {
        MongoCollection<Document> collection = Core.getInstance().getMongoManager().getRanks();

        collection.find().forEach((Consumer<? super Document>) doc -> {
            String name = doc.getString("name");
            String prefix = doc.getString("prefix");
            String suffix = doc.getString("suffix");
            int priority = doc.getInteger("priority");
            String color = doc.getString("color");
            boolean defaultRank = doc.getBoolean("defaultRank");
            List<String> permissions = doc.getList("permissions", String.class);
            List<String> inheritances = doc.getList("inheritances", String.class);

            new Rank(name, prefix, suffix, priority, color, defaultRank, permissions, inheritances);
        });
    }

    @SuppressWarnings("all")
    public void save() {
        Document document = new Document("name", this.name);

        document.put("name", this.name);
        document.put("prefix", this.prefix);
        document.put("suffix", this.suffix);
        document.put("priority", this.priority);
        document.put("color", this.color.toString());
        document.put("defaultRank", this.defaultRank);
        document.put("permissions", this.permissions);
        document.put("inheritances", this.inheritances);

        Bson filter = eq("name", this.name);
        FindIterable iterable = Core.getInstance().getMongoManager().getProfiles().find(filter);

        if (iterable.first() == null) {
            Core.getInstance().getMongoManager().getProfiles().insertOne(document);
        } else {
            Core.getInstance().getMongoManager().getProfiles().replaceOne(filter, document);
        }
    }

    public boolean addPermission(String permission) {
        if(!this.permissions.contains(permission)) {
            this.permissions.add(permission);
            return true;
        }

        return false;
    }

    public boolean removePermission(String permission) {
        if(this.permissions.contains(permission)) {
            this.permissions.remove(permission);
            return true;
        }

        return false;
    }

    public void delete(String rankName) {
        FindIterable<Document> foundResults = Core.getInstance().getMongoManager().getRanks().find();
        for (final Document doc : foundResults) {
            if (doc.getString("name").equals(rankName)) {
                Core.getInstance().getMongoManager().getRanks().deleteOne(doc);
            }
        }
    }

    public void destroy() {
        this.save();
        ranks.remove(this);
    }
}
