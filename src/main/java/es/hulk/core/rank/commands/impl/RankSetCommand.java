package es.hulk.core.rank.commands.impl;

import es.hulk.core.Core;
import es.hulk.core.profile.Profile;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import me.clip.placeholderapi.libs.kyori.adventure.platform.facet.Facet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankSetCommand extends BaseCommand {

    @Command(name = "rank.setrank", permission = "rank.setrank", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length == 0) {
            sender.sendMessage("/rank setrank <rank name> <player>");
            return;
        }
        
        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);
        Player targetPlayer = Bukkit.getPlayer(args[1]);

        if (targetPlayer == null) {
            sender.sendMessage("Player not found");
            return;
        }

        Profile profile = Profile.getProfile(targetPlayer);

        if (profile == null) {
            sender.sendMessage("Profile not found");
            return;
        }

        if (rank == null) {
            sender.sendMessage("Rank not found");
            return;
        }

        profile.setRank(rank);
        profile.save();
        sender.sendMessage("Rank set to " + rank.getName());
    }
}
