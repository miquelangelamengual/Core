package es.hulk.core.rank.commands;

import es.hulk.core.rank.Rank;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankListCommand extends BaseCommand {

    @Command(name = "rank.list", permission = "rank.list")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        for (Rank rank : Rank.getRanks().keySet()) {
            player.sendMessage(rank.getName());
        }
    }
}
