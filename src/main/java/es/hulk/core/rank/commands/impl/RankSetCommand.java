package es.hulk.core.rank.commands.impl;

import es.hulk.core.Core;
import es.hulk.core.profile.Profile;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import me.clip.placeholderapi.libs.kyori.adventure.platform.facet.Facet;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RankSetCommand extends BaseCommand {

    @Command(name = "rank.set", permission = "rank.set")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        Profile profile = Profile.getProfile(player);

        if (args.length == 0) {
            player.sendMessage("/rank set <rank name>");
            return;
        }
        
        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);

        if (profile == null) {
            player.sendMessage("Profile not found");
            return;
        }

        if (rank == null) {
            player.sendMessage("Rank not found");
            return;
        }

        profile.setRank(rank);
        rank.setColor(rank.getColor());
        profile.save();
        player.sendMessage("Rank set to " + rank.getName());
    }
}
