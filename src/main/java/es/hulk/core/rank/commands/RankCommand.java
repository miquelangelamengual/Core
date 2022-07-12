package es.hulk.core.rank.commands;

import es.hulk.core.utils.CC;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankCommand extends BaseCommand {

    @Command(name = "rank")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage("Rank Commands");
        player.sendMessage("/rank list - List all ranks");
        player.sendMessage("/rank create <rank name> - Create a rank");
        player.sendMessage(CC.CHAT_BAR);
    }
}
