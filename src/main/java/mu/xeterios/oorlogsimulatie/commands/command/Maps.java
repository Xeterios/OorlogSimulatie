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
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ "+ config.getPluginColor() + "&lMaps"));
        for (Map map : main.maps){
            TextComponent tc = new TextComponent(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "&l" + map.getMapName()));
            tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(String.valueOf(map.getSpawnDefenders()))));
            sender.sendMessage(tc);
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8└────── " + config.getPluginColor() + "&lOorlog Simulatie&8"));


    }
}
