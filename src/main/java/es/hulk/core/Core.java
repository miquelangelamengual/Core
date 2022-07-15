package es.hulk.core;

import es.hulk.core.listeners.ChatListener;
import es.hulk.core.database.MongoManager;
import es.hulk.core.profile.Profile;
import es.hulk.core.profile.listeners.ProfileListener;
import es.hulk.core.rank.Rank;
import es.hulk.core.rank.RankManager;
import es.hulk.core.rank.commands.RankCommandManager;
import es.hulk.core.utils.aquamenu.MenuManager;
import es.hulk.core.utils.aquamenu.listener.AquaMenuListener;
import es.hulk.core.utils.command.CommandManager;
import es.hulk.core.utils.config.FileConfig;
import es.hulk.core.utils.menu.MenuListener;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
public final class Core extends JavaPlugin {

    @Getter private static Core instance;

    private boolean isPlaceholderAPI = false;
    private MenuManager menuManager;
    private FileConfig settingsConfig;
    private MongoManager mongoManager;
    private CommandManager commandManager;
    private RankManager rankManager;
    private RankCommandManager rankCommandManager;

    @Override
    public void onEnable() {
        instance = this;

        this.settingsConfig = new FileConfig(this, "settings.yml");
        this.loadManagers();
        this.loadCommands();
        this.loadListeners();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            isPlaceholderAPI = true;
        }

        Rank.init();
    }

    @Override
    public void onDisable() {
        for (Profile profile : Profile.getProfiles().values()) {
            profile.save();
        }

        for (Rank rank : Rank.getRanks().keySet()) {
            rank.save();
        }
    }

    public void loadManagers() {
        this.mongoManager = new MongoManager(this);
        this.menuManager = new MenuManager(this);
        this.commandManager = new CommandManager(this);
        this.rankManager = new RankManager();
    }

    public void loadCommands() {
        this.rankCommandManager = new RankCommandManager();
    }

    public void loadListeners() {
        List<Listener> listeners = Arrays.asList(
                new ChatListener(),
                new ProfileListener(),
                new AquaMenuListener(),
                new MenuListener()
        );

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }
}
