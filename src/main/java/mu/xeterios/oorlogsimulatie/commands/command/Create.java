package mu.xeterios.oorlogsimulatie.commands.command;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class Create implements ICmd {

    private String[] args;

    public Create(String[] args){
        this.args = args;
    }

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        if (sender instanceof org.bukkit.entity.Player){
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            World world = ((org.bukkit.entity.Player)sender).getWorld();
            RegionManager man = container.get(BukkitAdapter.adapt(world));
            if (man == null){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Er zijn geen regions in de wereld waar jij bent.");
                return;
            }
            ProtectedRegion region = man.getRegion(args[1]);
            if (region == null){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze region bestaat niet.");
                return;
            }
            if (!(args.length >= 3)){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je hebt geen naam doorgegeven.");
                return;
            }
            boolean deniedByName = false;
            boolean deniedByRegion = false;
            for (Map map : main.maps){
                if (Objects.equals(args[2], map.getMapName())){
                    deniedByName = true;
                }
                if (Objects.equals(man.getRegion(map.getRegion().getId()).getId(), args[1])){
                    deniedByRegion = true;
                }
            }
            if (deniedByName && deniedByRegion){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze region is al gebonden aan een map en deze naam is al gebruikt.");
            } else if (deniedByName){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze naam is al gebruikt.");
            } else if (deniedByRegion){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze region is al gebonden aan een map.");
            }
            if (!deniedByName && !deniedByRegion){
                Map map = new Map(args[2], region);
                main.maps.add(map);
                sender.sendMessage(config.getPluginPrefix() + "New map created: " + map);
                config.SaveConfig();
            }
        } else {
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je kan dit alleen doen als een speler.");
        }
    }
}
