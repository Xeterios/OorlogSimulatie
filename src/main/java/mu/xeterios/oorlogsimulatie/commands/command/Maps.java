package mu.xeterios.oorlogsimulatie.commands.command;

import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Maps implements ICmd {

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager man = container.get(((Player)sender).getWorld());

    }
}
