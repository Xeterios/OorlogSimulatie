package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Setspawn implements ICmd {

    private final String[] args;

    public Setspawn(String[] args){
        this.args = args;
    }

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        if (sender instanceof Player){
            if (args.length > 1){
                Map toSetSpawnFor = null;
                for (Map map: main.maps){
                    if (Objects.equals(map.getMapName(), args[1])){
                        toSetSpawnFor = map;
                    }
                }
                if (toSetSpawnFor != null){
                    if (args.length > 2){
                        switch (args[2]){
                            case "attackers":
                                toSetSpawnFor.setSpawnAttackers(((Player)sender).getLocation());
                                sender.sendMessage(config.getPluginPrefix() + "Spawn gezet voor attackers: " + ((Player)sender).getLocation().getBlockX() + " " + ((Player)sender).getLocation().getBlockY() + " " + ((Player)sender).getLocation().getBlockZ() + ".");
                                config.SaveConfig();
                                break;
                            case "defenders":
                                toSetSpawnFor.setSpawnDefenders(((Player)sender).getLocation());
                                sender.sendMessage(config.getPluginPrefix() + "Spawn gezet voor defenders: " + ((Player)sender).getLocation().getBlockX() + " " + ((Player)sender).getLocation().getBlockY() + " " + ((Player)sender).getLocation().getBlockZ() + ".");
                                config.SaveConfig();
                                break;
                            default:
                                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Attackers of defenders?");
                                break;
                        }
                    } else {
                        sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Attackers of defenders?");
                    }
                } else {
                    sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze map bestaat niet.");
                }
            } else {
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je hebt geen naam doorgegeven.");
            }
        } else {
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je kan dit alleen doen als een speler.");
        }
    }
}
