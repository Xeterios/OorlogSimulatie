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
        if (sender.hasPermission("os.help")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " &8»"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &fLaat dit menu zien"));
        }
        if (sender.hasPermission("os.create")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " create <region> <naam> &8»"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &fMaak een nieuwe map aan"));
        }
        if (sender.hasPermission("os.setspawn")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " setspawn <map> <attackers/defenders> &8»"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &fZet de spawn voor een team"));
        }
        if (sender.hasPermission("os.setteam")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " setteam <speler> <map> <attacker/defender> &8»"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &fZet een speler in een team"));
        }
        if (sender.hasPermission("os.forcestart")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " forcestart <map> &8»"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &fStart een map, zelfs zonder de minimum spelers"));
        }
        if (sender.hasPermission("os.forcestop")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " forcestop <map> &8»"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &fStop een map als deze bezig is"));
        }
        if (sender.hasPermission("os.maps")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " maps &8»"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &fLaat een lijst met alle maps zien"));
        }
        if (sender.hasPermission("os.fillall")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/" + label + " fillall &8»"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &fVul beide teams met de helft van de mensen online"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &f&oGebruik os.fillallexempt om dit te vermijden"));
        }
        if (sender.hasPermission("os.leave")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ " + config.getPluginColor() + "/leave &8»"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│ &fLeave een game als je in eentje zit"));
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8│"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8└────── " + config.getPluginColor() + "&lOorlog Simulatie&8"));
    }
}