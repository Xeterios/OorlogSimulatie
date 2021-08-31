package mu.xeterios.oorlogsimulatie;

import mu.xeterios.oorlogsimulatie.commands.handlers.CommandHandler;
import mu.xeterios.oorlogsimulatie.config.Config;
import mu.xeterios.oorlogsimulatie.game.OS;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class Main extends JavaPlugin {

    private OS game;
    private Config config;
    public ArrayList<Map> maps;

    public File customConfigFile;
    public FileConfiguration customConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        createCustomConfig();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        this.maps = new ArrayList<>();
        this.config = new Config(this, this);
        this.game = new OS();
        this.getCommand("oorlogsimulatie").setExecutor(new CommandHandler(this, config));
    }

    public void onDisable() {
        // Plugin shutdown logic
        this.game.Stop();
        config.SaveConfig();
        config.ReloadConfig();
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "maps.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("maps.yml", false);
        }
        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public OS getGame() {
        return game;
    }

    public Config GetConfig() {
        return config;
    }

}
