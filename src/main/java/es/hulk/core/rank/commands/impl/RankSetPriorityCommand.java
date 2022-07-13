package es.hulk.core.rank.commands.impl;

import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankSetPriorityCommand extends BaseCommand {

    @Command(name = "rank.setpriority", permission = "rank.setpriority")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage("/rank setpriority <rank> <priority>");
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);

        if (rank == null) {
            player.sendMessage("Rank not found");
            return;
        }

        int priority = Integer.parseInt(args[1]);
        rank.setPriority(priority);
        player.sendMessage("Rank priority set to " + priority);
    }
}
