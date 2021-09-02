package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Leave implements ICmd {

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        if (sender instanceof Player){
            Player target = (Player) sender;
            boolean isAttacker = false;
            boolean isDefender = false;
            for(Player p : main.getGame().getAttackers()){
                if (p.getUniqueId() == target.getUniqueId()){
                    isAttacker = true;
                }
            }
            for(Player p : main.getGame().getDefenders()){
                if (p.getUniqueId() == target.getUniqueId()){
                    isDefender = true;
                }
            }
            if (isAttacker){
                main.getGame().getAttackers().remove(target);
                target.sendMessage(config.getPluginPrefix() + "Je bent de attackers verlaten.");
                return;
            }
            if (isDefender){
                main.getGame().getDefenders().remove(target);
                target.sendMessage(config.getPluginPrefix() + "Je bent de defenders verlaten.");
                return;
            }
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je zit momenteel niet in een team.");
        } else {
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je kan dit alleen doen als een speler.");
        }
    }
}
