package es.hulk.core.rank.commands.impl;

import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankAddPermCommand extends BaseCommand {

    @Command(name = "rank.addperm", permission = "rank.addperm", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length == 0) {
            sender.sendMessage("/rank addperm <rank> <perm>");
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);

        if (rank == null) {
            sender.sendMessage("Rank not found");
            return;
        }

        String perm = args[1];

        if (!rank.addPermission(perm)) {
            sender.sendMessage("Permission already exists");
            return;
        }

        sender.sendMessage("Permission added");

        for (Player p : rankManager.getPlayersWithRank(rank)) {
            p.sendMessage("You have been granted the permission " + perm);
            rankManager.updatePermissions(p);
        }

        sender.sendMessage("Rank perm added: " + perm);
    }
}
