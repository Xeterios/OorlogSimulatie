package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Queue implements ICmd {

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        if (sender instanceof Player){
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command;
            if (sender.hasPermission("os.queued")){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je bent uit de queue gezet.");
                command = "lp user " + sender.getName() + " permission set os.queued false";
            } else {
                sender.sendMessage(config.getPluginPrefix() + ChatColor.GREEN + "Je bent in de queue gezet.");
                command = "lp user " + sender.getName() + " permission set os.queued true";
            }
            Bukkit.dispatchCommand(console, command);
        } else {
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je kan dit alleen doen als een speler.");
        }
    }
}
