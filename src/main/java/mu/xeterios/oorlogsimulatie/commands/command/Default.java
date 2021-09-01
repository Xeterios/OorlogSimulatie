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
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " &8»"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &7Laat dit menu zien"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " create <region> <naam> &8»"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &7&7Maak een nieuwe map aan"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " setspawn <map> <attackers/defenders> &8»"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &7Zet de spawn voor een team"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " setteam <speler> <map> <attacker/defender> &8»"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &7Zet een speler in een team"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " forcestart <map> &8»"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &7Start een map, zelfs zonder de minimum spelers"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " forcestop <map> &8»"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &7Stop een map als deze bezig is"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " maps &8»"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &7Laat een lijst met alle maps zien"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8└────── " + config.getPluginColor() + "&lOorlog Simulatie&8"));
    }
}
