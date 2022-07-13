package es.hulk.core.rank.commands;

import es.hulk.core.utils.CC;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.entity.Player;

public class RankCommand extends BaseCommand {

    @Command(name = "rank", permission = "rank.help")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();

        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage("Rank Commands");
        player.sendMessage("/rank list - List all ranks");
        player.sendMessage("/rank create <rank name> - Create a rank");
        player.sendMessage("/rank setrank <rank name> <player> - Create a rank");
        player.sendMessage("/rank setprefix <rank name> <prefix> - Set a rank prefix");
        player.sendMessage("/rank setcolor <rank name> <color> - Set a rank color");
        player.sendMessage("/rank setpriority <rank name> <priority> - Set a rank priority");
        player.sendMessage("/rank setdefault <rank name> - Set a rank as default");
        player.sendMessage("/rank setsuffix <rank name> <suffix> - Set a rank suffix");
        player.sendMessage("/rank delete <rank name> - Delete a rank");
        player.sendMessage("/rank addperm <rank name> <permission> - Add a permission to a rank");
        player.sendMessage(CC.CHAT_BAR);
    }
}
