package es.hulk.core.rank.commands.impl;

import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

public class RankSetSuffixCommand extends BaseCommand {

    @Command(name = "rank.setsuffix", permission = "rank.setsuffix")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage("/rank setsuffix <color>");
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);

        if (rank == null) {
            player.sendMessage("Rank not found");
            return;
        }

        String suffix = StringUtils.join(args, ' ', 1, args.length);
        rank.setSuffix(suffix);
        player.sendMessage("Rank suffix set to " + suffix);
    }
}
