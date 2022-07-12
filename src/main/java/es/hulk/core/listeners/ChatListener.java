package es.hulk.core.listeners;

import es.hulk.core.Core;
import es.hulk.core.profile.Profile;
import es.hulk.core.rank.Rank;
import es.hulk.core.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    public ChatListener() {
        Bukkit.getPluginManager().registerEvents(this, Core.getInstance());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = Profile.getProfile(player);

        if (profile == null) return;

        Rank rank = profile.getRank();
        event.setFormat(CC.translate(rank.getPrefix() + ChatColor.valueOf(rank.getColor()) + profile.getName() + "&7: &r" + event.getMessage()));
    }

}
