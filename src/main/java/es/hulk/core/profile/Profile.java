package es.hulk.core.profile;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import es.hulk.core.Core;
import es.hulk.core.profile.social.objects.SocialObject;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

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
    private SocialObject social;

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
        this.social = SocialObject.fromJSON(document.getString("social"));
    }

    @SuppressWarnings("all")
    public void save() {
        Document document = new Document();

        Rank.getRanks().keySet().forEach(Rank::save);

        document.put("name", this.name);
        document.put("uuid", this.uuid.toString());
        document.put("rank", this.rank.toJSON());
        document.put("social", this.social.toJSON());

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
        profiles.remove(Bukkit.getPlayer(this.getUuid()));
    }

}
