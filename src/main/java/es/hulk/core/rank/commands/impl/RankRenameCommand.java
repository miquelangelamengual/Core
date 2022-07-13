package es.hulk.core.rank.commands.impl;

import es.hulk.core.Core;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.CommandArgs;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

public class RankRenameCommand extends BaseCommand {
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage("/rank rename <rank> <new name>");
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);

        if (rank == null) {
            player.sendMessage("Rank not found");
            return;
        }

        String name = StringUtils.join(args, ' ', 1, args.length);
        rank.setName(name);
        player.sendMessage("Rank name set to " + name);
    }
}
