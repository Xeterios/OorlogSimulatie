package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Forcestop implements ICmd {


    private String[] args;

    public Forcestop(String[] args){
        this.args = args;
    }

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        if (main.getGame().isStarted()){
            main.getGame().Stop();
            sender.sendMessage(config.getPluginPrefix() + "De game is gestopt.");
        } else {
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "De game is niet bezig.");
        }
    }
}
