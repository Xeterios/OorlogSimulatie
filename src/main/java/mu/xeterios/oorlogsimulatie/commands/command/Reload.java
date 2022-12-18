package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Reload implements ICmd {

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        main.getGame().getDefenders().clear();
        main.getGame().getAttackers().clear();
        main.getGame().getPlayers().clear();
        main.getGame().StopListener();
        main.getGame().setInitialized(false);
        main.getGame().getParticleHandler().disableParticles();
        main.getGame().StopPreGameListener();

        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> {
            main.getGame().Stop();
            config.ReloadConfig();
            sender.sendMessage(config.getPluginPrefix() + ChatColor.GREEN + "Plugin successvol gereload!");
        }, 30L);

    }
}
