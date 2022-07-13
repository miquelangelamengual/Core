package es.hulk.core.rank.commands.impl;

import es.hulk.core.rank.Rank;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RankListCommand extends BaseCommand {

    @Command(name = "rank.list", permission = "rank.list")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        player.sendMessage("");
        player.sendMessage("Ranks:");
        for (Rank rank : Rank.getRanks().keySet()) {
            player.sendMessage(rank.getName() + " - " + ChatColor.valueOf(rank.getColor()) + rank.getColor() + " " + rank.getPrefix());
        }
        player.sendMessage("");
    }
}
