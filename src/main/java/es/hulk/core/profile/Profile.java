package es.hulk.core.profile;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

@Getter
@Setter
public class Profile {

    @Getter
    private static Map<Player, Profile> profiles = Maps.newHashMap();

    public static Profile getProfile(Player player) {
        return profiles.computeIfAbsent(player, key ->{
            Profile profile = new Profile(key);
            profile.setRank(Core.getInstance().getRankManager().getDefaultRank());

            profiles.put(key, profile);
            return profile;
        });
    }

    private UUID uuid;
    private String name;
    private Rank rank;

    public Profile(Player player) {
        this.uuid = player.getUniqueId();
        this.name = player.getName();
    }

    public void init() {
        MongoCollection<Document> collection = Core.getInstance().getMongoManager().getProfiles();
        Document document = collection.find(eq("uuid", uuid.toString())).first();

        if (document == null) return;

        this.name = document.getString("name");
        this.rank = Rank.fromJSON(document.getString("rank"));
    }

    @SuppressWarnings("all")
    public void save() {
        Document document = new Document();

        document.put("name", this.name);
        document.put("uuid", this.uuid.toString());
        Rank.getRanks().keySet().forEach(Rank::save);

        document.put("rank", this.rank.toJSON());

        Core.getInstance().getMongoManager().getProfiles().replaceOne(
                eq("uuid", this.uuid.toString()),
                document,
                new UpdateOptions().upsert(true)
        );

        RankManager rankManager = Core.getInstance().getRankManager();
        for (Player p : rankManager.getPlayersWithRank(rank)) {
            rankManager.updatePermissions(p);
        }
    }

    public void destroy() {
        this.save();
        profiles.remove(this);
    }

}
