package es.hulk.core.profile;

import com.mongodb.client.model.Filters;
import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class ProfileListener implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player);
        profile.setRank(Rank.fromJSON(Objects.requireNonNull(Core.getInstance().getMongoManager().getProfiles().find(Filters.eq("uuid", player.getUniqueId().toString())).first()).getString("rank")));

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(profile.getRank().getName());

        if(rank == null) {
            Rank defaultRank = rankManager.getDefaultRank();
            if(defaultRank != null) profile.setRank(defaultRank);
        }
        rankManager.updatePermissions(player);

        profile.init();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player);
        Rank rank = profile.getRank();
        RankManager rankManager = Core.getInstance().getRankManager();

        player.sendMessage(rank.getPermissions().toString());

        for (Player p : rankManager.getPlayersWithRank(rank)) {
            System.out.println("Player " + p.getName() + " has rank " + rank.getName());
            rankManager.updatePermissions(p);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Profile profile = Profile.getProfile(event.getPlayer());
        if (profile == null) return;
        profile.destroy();
    }
}
