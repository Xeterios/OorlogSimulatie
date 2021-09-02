package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Forcestart implements ICmd {

    private String[] args;

    public Forcestart(String[] args){
        this.args = args;
    }

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        if (!main.getGame().isStarted()){
            if (!main.getGame().Start()){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Er moet minstens 1 persoon in elk team zitten.");
            }
            sender.sendMessage(config.getPluginPrefix() + "De game start over 60 seconden.");
        } else {
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "De game is al bezig.");
        }
    }
}
