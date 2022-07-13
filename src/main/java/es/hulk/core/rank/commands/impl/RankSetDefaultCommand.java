package es.hulk.core.rank.commands.impl;

import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankSetDefaultCommand extends BaseCommand {

    @Command(name = "rank.setdefault", permission = "rank.setdefault")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage("/rank setdefault <rank>");
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);

        if (rank == null) {
            player.sendMessage("Rank not found");
            return;
        }

        rank.setDefaultRank(!rank.isDefaultRank());
        player.sendMessage("Default rank set to " + rank.getName());
    }
}
