package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Default implements ICmd {

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8┌────── " + config.getPluginColor() + "&lOorlog Simulatie&8"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " &8» &fDisplay this menu"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " start <world> &8» &fStart Murder Mystery"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " stop &8» &fStop Murder Mystery"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8└────── " + config.getPluginColor() + "&lOorlog Simulatie&8"));
    }
}
