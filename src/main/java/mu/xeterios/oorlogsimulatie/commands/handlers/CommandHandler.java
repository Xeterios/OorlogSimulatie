package mu.xeterios.oorlogsimulatie.commands.handlers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.commands.CommandFactory;
import mu.xeterios.oorlogsimulatie.commands.PermissionType;
import mu.xeterios.oorlogsimulatie.commands.command.Default;
import mu.xeterios.oorlogsimulatie.config.Config;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final Config config;
    private final Main plugin;

    public CommandHandler(Main plugin, Config config){
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PermissionHandler handler = new PermissionHandler(sender);
        PermissionType type = handler.CheckPermission(command.getLabel());
        String[] newArgs = { command.getLabel() };
        if (args.length > 0 && type == PermissionType.ALLOWED){
            type = handler.CheckPermission(args[0]);
            CheckPermission(sender, args, label, type);
        } else {
            CheckPermission(sender, newArgs, label, type);
        }
        return true;
    }

    public void CheckPermission(CommandSender sender, String[] args, String label, PermissionType type) {
        switch (type) {
            case ALLOWED:
                CommandFactory cmdFactory = new CommandFactory();
                ICmd cmd = cmdFactory.GetCommand(args);
                if (cmd != null) {
                    cmd.Execute(sender, label, plugin, config);
                }
                break;
            case NOPERM:
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "You have no permission to use this command.");
                break;
            case UNKNOWN:
                Default df = new Default();
                df.Execute(sender, label, plugin, config);
                break;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getLabel().equalsIgnoreCase("oorlogsimulatie") || command.getLabel().equalsIgnoreCase("os")){
            PermissionHandler handler = new PermissionHandler(sender);
            ArrayList<String> toReturn = new ArrayList<>();
            if (args.length == 1){
                // Possibly implement
                if (handler.CheckPermission("create") == PermissionType.ALLOWED){
                    toReturn.add("create");
                }
                if (handler.CheckPermission("forcestart") == PermissionType.ALLOWED){
                    toReturn.add("forcestart");
                }
                if (handler.CheckPermission("forcestop") == PermissionType.ALLOWED){
                    toReturn.add("forcestop");
                }
                if (handler.CheckPermission("maps") == PermissionType.ALLOWED){
                    toReturn.add("maps");
                }
                if (handler.CheckPermission("setspawn") == PermissionType.ALLOWED){
                    toReturn.add("setspawn");
                }
                if (handler.CheckPermission("setteam") == PermissionType.ALLOWED){
                    toReturn.add("setteam");
                }
                if (handler.CheckPermission("fillall") == PermissionType.ALLOWED){
                    toReturn.add("fillall");
                }
            }
            if (args.length == 2){
                // Possibly implement
                if (args[0].equals("create") && handler.CheckPermission("create") == PermissionType.ALLOWED){
                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    World world = ((org.bukkit.entity.Player)sender).getWorld();
                    RegionManager man = container.get(BukkitAdapter.adapt(world));
                    assert man != null;
                    Set<String> keys = man.getRegions().keySet();
                    toReturn.addAll(keys);
                }
                if (args[0].equals("setspawn") && handler.CheckPermission("setspawn") == PermissionType.ALLOWED){
                    for(Map map : config.main.maps){
                        toReturn.add(map.getMapName());
                    }
                }
                if (args[0].equals("setteam") && handler.CheckPermission("setteam") == PermissionType.ALLOWED){
                    for(Player p : Bukkit.getOnlinePlayers()){
                        toReturn.add(p.getName());
                    }
                }
                if (args[0].equals("fillall") && handler.CheckPermission("fillall") == PermissionType.ALLOWED){
                    for(Map map : config.main.maps){
                        toReturn.add(map.getMapName());
                    }
                }
            }
            if (args.length == 3){
                if (args[0].equals("setspawn")  && handler.CheckPermission("setspawn") == PermissionType.ALLOWED){
                    toReturn.add("attackers");
                    toReturn.add("defenders");
                }
                if (args[0].equals("setteam") && handler.CheckPermission("setteam") == PermissionType.ALLOWED){
                    for(Map map : config.main.maps){
                        toReturn.add(map.getMapName());
                    }
                }
            }
            if (args.length == 4){
                if (args[0].equals("setteam") && handler.CheckPermission("setteam") == PermissionType.ALLOWED){
                    toReturn.add("attackers");
                    toReturn.add("defenders");
                }
            }
            return toReturn;
        }
        return null;
    }
}
