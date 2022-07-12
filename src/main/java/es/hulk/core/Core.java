package es.hulk.core;

import es.hulk.core.mongo.MongoManager;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.commands.RankCommand;
import es.hulk.core.rank.commands.RankCreateCommand;
import es.hulk.core.rank.commands.RankListCommand;
import es.hulk.core.utils.aquamenu.MenuManager;
import es.hulk.core.utils.command.CommandManager;
import es.hulk.core.utils.config.FileConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

@Getter @Setter
public final class Core extends JavaPlugin {

    @Getter private static Core instance;

    private boolean isPlaceholderAPI = false;
    private MenuManager menuManager;
    private FileConfig settingsConfig;
    private MongoManager mongoManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;

        this.settingsConfig = new FileConfig(this, "settings.yml");
        this.loadManagers();
        this.loadCommands();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            isPlaceholderAPI = true;
        }

        Rank.init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void loadManagers() {
        this.mongoManager = new MongoManager(this);
        this.menuManager = new MenuManager(this);
        this.commandManager = new CommandManager(this);
    }

    public void loadCommands() {
        new RankCommand();
        new RankCreateCommand();
        new RankListCommand();
    }
}
