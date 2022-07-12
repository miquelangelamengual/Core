package es.hulk.core.rank.commands;

import es.hulk.core.rank.Rank;
import es.hulk.core.utils.command.BaseCommand;
import es.hulk.core.utils.command.Command;
import es.hulk.core.utils.command.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RankCreateCommand extends BaseCommand {

    @Command(name = "rank.create", permission = "rank.create")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        Rank rank = new Rank(args[0]);
        player.sendMessage("Rank " + rank.getName() + " created");
        rank.save();
    }
}
