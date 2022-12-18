package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import mu.xeterios.oorlogsimulatie.game.OS;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Fillall implements ICmd {

    private final String[] args;

    public Fillall(String[] args){
        this.args = args;
    }

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        if (args.length > 1){
            boolean cancelCommand = false;
            Map selected = null;
            for(Map map : main.maps){
                if (Objects.equals(map.getMapName(), args[1])){
                    selected = map;
                }
            }
            if (selected == null){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze map bestaat niet.");
                cancelCommand = true;
            } else {
                if (selected.getSpawnAttackers() == null){
                    sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze map heeft geen attacker spawn gezet.");
                    cancelCommand = true;
                }
                if (selected.getSpawnDefenders() == null){
                    sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze map heeft geen defender spawn gezet.");
                    cancelCommand = true;
                }
            }
            if (cancelCommand) {
                return;
            }
            ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
            ArrayList<Player> eligiblePlayers = new ArrayList<>();
            for(Player player : onlinePlayers){
                if (!player.hasPermission("os.fillallexempt") && player.hasPermission("os.queued")){
                    eligiblePlayers.add(player);
                }
            }
            OS game = main.getGame();
            Random rnd = new Random();
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            if (eligiblePlayers.size() <= 0){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Er zit niemand in queue.");
                return;
            }
            while (game.getAttackers().size() + game.getDefenders().size() != eligiblePlayers.size()){
                int id = rnd.nextInt(eligiblePlayers.size());
                Player player = eligiblePlayers.get(id);

                boolean eligiblePlayerIsNew = true;
                for(ArrayList<Player> list : Arrays.asList(game.getAttackers(), game.getDefenders())){
                    for(Player p : list){
                        if (p.getName().equals(player.getName())){
                            eligiblePlayerIsNew = false;
                        }
                    }
                }
                if (eligiblePlayerIsNew){
                    String command;
                    if (game.getAttackers().size() >= game.getDefenders().size()){
                        command = "os setteam " + player.getName() + " " + selected.getMapName() + " defenders";
                    } else {
                        command = "os setteam " + player.getName() + " " + selected.getMapName() + " attackers";
                    }
                    Bukkit.dispatchCommand(console, command);
                }
            }
        } else {
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je hebt geen map doorgegeven.");
        }
    }
}