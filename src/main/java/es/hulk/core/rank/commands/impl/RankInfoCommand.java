package es.hulk.core.rank.commands.impl;

import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class RankInfoCommand extends BaseCommand {

    @Command(name = "rank.info", permission = "rank.info", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length == 0) {
            sender.sendMessage("/rank info <rank>");
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);

        if (rank == null) {
            sender.sendMessage("Rank not found");
            return;
        }

        sender.sendMessage("");
        sender.sendMessage("Rank Information:");
        sender.sendMessage("");
        sender.sendMessage("Rank: " + rank.getName());
        sender.sendMessage("Default rank: " + rank.isDefaultRank());
        sender.sendMessage("Permissions: " + rank.getPermissions().toString());
        sender.sendMessage("Players: " + rankManager.getPlayersWithRank(rank).toString());
        sender.sendMessage("Prefix: " + rank.getPrefix());
        sender.sendMessage("Suffix: " + rank.getSuffix());
        sender.sendMessage("Priority: " + rank.getPriority());
        sender.sendMessage("Rank color: " + rank.getColor());
        sender.sendMessage("");
    }
}
