package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import mu.xeterios.oorlogsimulatie.map.Map;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Maps implements ICmd {

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8┌────── " + config.getPluginColor() + "&lOorlog Simulatie&8"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│"));
        if (main.maps.size() > 0){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ "+ config.getPluginColor() + "Maps:"));
            for (Map map : main.maps){
                TextComponent tc = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&8│ &c" + map.getMapName() + " &7[Info]"));
                StringBuilder sb = new StringBuilder();
                sb.append(ChatColor.RED + "Region: " + ChatColor.GRAY + map.getRegion().getId() + "\n");
                if (map.getSpawnDefenders() != null){
                    sb.append(ChatColor.RED + "Defender spawn: " + ChatColor.GRAY + map.getSpawnDefenders().getWorld().getName() + ": " + map.getSpawnDefenders().getBlockX() + " " + map.getSpawnDefenders().getBlockY() + " " + map.getSpawnDefenders().getBlockZ() + "\n");
                } else {
                    sb.append(ChatColor.RED + "Defender spawn: " + ChatColor.GRAY + "" + ChatColor.UNDERLINE + "Not set!\n");
                }
                if (map.getSpawnAttackers() != null){
                    sb.append(ChatColor.RED + "Attacker spawn: " + ChatColor.GRAY + map.getSpawnAttackers().getWorld().getName() + ": " + map.getSpawnAttackers().getBlockX() + " " + map.getSpawnAttackers().getBlockY() + " " + map.getSpawnAttackers().getBlockZ() + "\n");
                } else {
                    sb.append(ChatColor.RED + "Attacker spawn: " + ChatColor.GRAY + "" + ChatColor.UNDERLINE + "Not set!\n");
                }
                tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(sb.toString())));
                sender.sendMessage(tc);
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ "+ config.getPluginColor() + "There are no maps!"));
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8└────── " + config.getPluginColor() + "&lOorlog Simulatie&8"));
    }
}
