package mu.xeterios.oorlogsimulatie.config;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class Config {

    private final Plugin plugin;
    private final Main main;

    private String pluginPrefix;
    private String pluginColor;

    public Config(Plugin plugin, Main main){
        this.plugin = plugin;
        this.main = main;
        LoadData();
        LoadMaps();
    }

    public void LoadData(){
        this.pluginPrefix = ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("locale.prefix"));
        this.pluginColor = this.plugin.getConfig().getString("locale.color");
    }

    public void LoadMaps(){
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        for(World world : Bukkit.getWorlds()){
            RegionManager man = container.get(BukkitAdapter.adapt(world));
            if (man != null){
                for(String string : main.getCustomConfig().getKeys(false)){
                    if (main.getCustomConfig().get(string) != null){
                        Map map = new Map(string, man.getRegion(string));
                        main.maps.add(map);
                    }
                }
            }
        }
    }

    public void SaveConfig(){
        this.plugin.getConfig().set("locale.prefix", this.pluginPrefix);
        this.plugin.getConfig().set("locale.color", this.pluginColor);
        this.plugin.saveConfig();
    }

    public void ReloadConfig(){
        this.plugin.reloadConfig();
        LoadData();
    }

    public String getPluginPrefix() {
        return pluginPrefix;
    }

    public String getPluginColor() {
        return pluginColor;
    }
}
