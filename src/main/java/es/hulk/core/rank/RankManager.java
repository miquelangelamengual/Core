package es.hulk.core.rank;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import es.hulk.core.Core;
import es.hulk.core.profile.Profile;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class RankManager {

    @Getter
    private Map<UUID, PermissionAttachment> permissions;
    @Getter
    private Map<UUID, List<String>> playerPermissions;

    public RankManager() {
        this.permissions = Maps.newConcurrentMap();
        this.playerPermissions = Maps.newConcurrentMap();
    }

    public Rank getDefaultRank() {
        Rank defaultRank;
        if (this.getRank("user") == null) {
            defaultRank = new Rank("user");
            defaultRank.setDefaultRank(true);
            defaultRank.setPrefix("");
            defaultRank.setSuffix("");
            defaultRank.setPriority(0);
            defaultRank.setColor("WHITE");
            defaultRank.setPermissions(Lists.newArrayList());
            defaultRank.setInheritances(Lists.newArrayList());
            defaultRank.save();
        } else {
            defaultRank = this.getRank("user");
        }
        return defaultRank;
    }

    public Rank getRank(String name) {
        for (Map.Entry<Rank, String> entry : Rank.getRanks().entrySet()) {
            if (entry.getValue().equals(name)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void addPermissions(Player player, List<String> permissions) {
        PermissionAttachment permissionAttachment = player.addAttachment(Core.getInstance());

        for (String perm : permissions) {
            if (!permissionAttachment.getPermissions().containsKey(perm))
                permissionAttachment.setPermission(perm, true);
        }
    }

    public void updatePermissions(Player player) {
        this.clearPermissions(player);

        Profile profile = Profile.getProfile(player);
        Rank rank = profile.getRank();
        List<String> rankPerms = rank.getPermissions();

        player.sendMessage(rankPerms.toString());
        this.addPermissions(player, rankPerms);
    }

    //Method to get all the players with the same rank
    public List<Player> getPlayersWithRank(Rank rank) {
        List<Player> players = Lists.newArrayList();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Profile.getProfile(player).getRank().equals(rank)) {
                players.add(player);
            }
        }
        return players;
    }

    public void clearPermissions(Player player) {
        this.permissions.getOrDefault(player.getUniqueId(), player.addAttachment(Core.getInstance())).getPermissions().clear();
        this.permissions.getOrDefault(player.getUniqueId(), player.addAttachment(Core.getInstance())).remove();
        this.permissions.remove(player.getUniqueId());
    }
}
