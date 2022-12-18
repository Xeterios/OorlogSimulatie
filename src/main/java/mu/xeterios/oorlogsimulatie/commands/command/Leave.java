package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Leave implements ICmd {

    private Main main;

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        if (sender instanceof Player){
            this.main = main;
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
                main.getGame().RemoveAttacker(target);
                main.getGame().getPlayers().remove(target);
                target.getInventory().clear();
                target.sendMessage(config.getPluginPrefix() + "Je bent de attackers verlaten.");
                resetPlayer(sender, main);
                return;
            }
            if (isDefender){
                main.getGame().RemoveDefender(target);
                main.getGame().getPlayers().remove(target);
                target.getInventory().clear();
                target.sendMessage(config.getPluginPrefix() + "Je bent de defenders verlaten.");
                resetPlayer(sender, main);
            }
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je zit momenteel niet in een team.");
        } else {
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je kan dit alleen doen als een speler.");
        }
    }

    private void resetPlayer(CommandSender sender, Main main) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String command = "lp user " + sender.getName() + " permission set os.queued false";
        Bukkit.dispatchCommand(console, command);
        String command2 = "spawn " + sender.getName();
        Bukkit.dispatchCommand(console, command2);
        if (areTeamsEmptyNow()){
            main.getGame().StopListener();
            main.getGame().setInitialized(false);
            main.getGame().getParticleHandler().disableParticles();
        }
    }

    public boolean areTeamsEmptyNow(){
        return main.getGame().getAttackers().isEmpty() & main.getGame().getAttackers().isEmpty();
    }
}
