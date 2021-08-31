package mu.xeterios.oorlogsimulatie.config;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

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
            assert man != null;
            java.util.Map<String, ProtectedRegion> regions = man.getRegions();
            if (regions.size() > 0){
                for(String string : main.getCustomConfig().getKeys(false)){
                    if (main.getCustomConfig().get(string) != null){
                        Map map = new Map(string, man.getRegion(string));
                        main.maps.add(map);
                    }
                    for (Player p : world.getPlayers()){
                        p.sendMessage(string + "  " + man.getRegion(string));
                    }
                }
            }
        }
    }

    public void SaveConfig(){
        this.plugin.getConfig().set("locale.prefix", this.pluginPrefix);
        this.plugin.getConfig().set("locale.color", this.pluginColor);
        this.plugin.saveConfig();

        for(Map map : main.maps){
            main.getCustomConfig().set(map.getMapName() + ".region", map.getRegion().getId());
            main.getCustomConfig().set(map.getMapName() + ".spawn.attackers", map.getSpawnAttackers());
            main.getCustomConfig().set(map.getMapName() + ".spawn.defenders", map.getSpawnDefenders());
        }
        try {
            main.customConfig.save(main.customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
