package es.hulk.core.rank.commands.impl;

import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

public class RankSetPrefixCommand extends BaseCommand {

    @Command(name = "rank.setprefix", permission = "rank.setprefix")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage("/rank setprefix <color>");
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);

        if (rank == null) {
            player.sendMessage("Rank not found");
            return;
        }

        String prefix = StringUtils.join(args, ' ', 1, args.length);
        rank.setPrefix(prefix);
        player.sendMessage("Rank prefix set to " + prefix);
    }
}
