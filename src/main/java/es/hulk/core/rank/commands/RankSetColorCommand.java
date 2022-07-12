package es.hulk.core.rank.commands;

import es.hulk.core.Core;
import es.hulk.core.profile.Profile;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.rank.menus.ColorMenu;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RankSetColorCommand extends BaseCommand {

    @Command(name = "rank.setcolor", permission = "rank.setcolor")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage("/rank setcolor <rank>");
            return;
        }

        RankManager rankManager = Core.getInstance().getRankManager();
        Rank rank = rankManager.getRank(args[0]);

        if (rank == null) {
            player.sendMessage("Rank not found");
            return;
        }

        new ColorMenu(rank).open(player);
        player.sendMessage("Rank color set to " + args[1]);
    }
}
