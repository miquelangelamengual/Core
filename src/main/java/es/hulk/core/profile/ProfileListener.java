package es.hulk.core.profile;

import com.mongodb.client.model.Filters;
import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class ProfileListener implements Listener {

    public ProfileListener() {
        Bukkit.getPluginManager().registerEvents(this, Core.getInstance());
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player);
        profile.setRank(Rank.fromJSON(Objects.requireNonNull(Core.getInstance().getMongoManager().getProfiles().find(Filters.eq("uuid", player.getUniqueId().toString())).first()).getString("rank")));
        profile.init();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Profile profile = Profile.getProfile(event.getPlayer());
        if (profile == null) return;
        profile.destroy();
    }
}
