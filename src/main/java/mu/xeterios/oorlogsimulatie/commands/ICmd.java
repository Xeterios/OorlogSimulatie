package mu.xeterios.oorlogsimulatie.commands;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.config.Config;
import org.bukkit.command.CommandSender;

public interface ICmd {

    void Execute(CommandSender sender, String label, Main main, Config config);
}
