package mu.xeterios.oorlogsimulatie.commands.command;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.commands.ICmd;
import mu.xeterios.oorlogsimulatie.config.Config;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Setteam implements ICmd {

    private String[] args;

    public Setteam(String[] args){
        this.args = args;
    }

    @Override
    public void Execute(CommandSender sender, String label, Main main, Config config) {
        if (args.length > 1){
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null){
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze speler bestaat niet.");
                return;
            }
            if (args.length > 2){
                Map selected = null;
                for(Map map : main.maps){
                    if (Objects.equals(map.getMapName(), args[2])){
                        selected = map;
                    }
                }
                if (selected == null){
                    sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze map bestaat niet.");
                    return;
                }

                if (args.length > 3){
                    switch (args[3]){
                        case "attackers":
                            if (selected.getSpawnAttackers() == null){
                                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze map heeft geen attacker spawn gezet.");
                                return;
                            }
                            boolean isDefender = false;
                            for(Player p : main.getGame().getDefenders()){
                                if (p.getUniqueId() == target.getUniqueId()){
                                    isDefender = true;
                                }
                            }
                            if (isDefender){
                                main.getGame().getDefenders().remove(target);
                                main.getGame().getPlayers().remove(target);
                                if (sender == target){
                                    sender.sendMessage(config.getPluginPrefix() + "Jij bent uit de defenders gezet.");
                                } else {
                                    sender.sendMessage(config.getPluginPrefix() + target.getName() + " is uit de defenders gezet.");
                                }
                            }
                            for(Player p : main.getGame().getAttackers()){
                                if (p.getUniqueId() == target.getUniqueId()){
                                    sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze speler zit al bij de attackers.");
                                    return;
                                }
                            }
                            main.getGame().setMap(selected);
                            main.getGame().AddAttacker(target);
                            main.getGame().AddPlayer(target);
                            target.teleport(selected.getSpawnAttackers());
                            target.setBedSpawnLocation(selected.getSpawnAttackers());
                            target.setGameMode(GameMode.SURVIVAL);
                            target.setScoreboard(main.getGame().getScoreboard());
                            if (main.getGame().isStarted()){
                                main.getGame().giveKit(target);
                            }
                            if (main.getGame().getSaveOldScoreboard() == null){
                                main.getGame().setSaveOldScoreboard(target.getScoreboard());
                            }
                            if (sender == target){
                                sender.sendMessage(config.getPluginPrefix() + "Jij bent toegevoegd aan de attackers.");
                            } else {
                                sender.sendMessage(config.getPluginPrefix() + target.getName() + " is toegevoegd aan de attackers.");
                            }
                            for(Player p : main.getGame().getPlayers()){
                                p.sendMessage(config.getPluginPrefix() + ChatColor.YELLOW + target.getName() + ChatColor.translateAlternateColorCodes('&', config.getPluginColor()) + " is nu een " + ChatColor.RED + "attacker.");
                            }
                            break;
                        case "defenders":
                            if (selected.getSpawnDefenders() == null){
                                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze map heeft geen defender spawn gezet.");
                                return;
                            }
                            boolean isAttacker = false;
                            for(Player p : main.getGame().getAttackers()){
                                if (p.getUniqueId() == target.getUniqueId()){
                                    isAttacker = true;
                                }
                            }
                            if (isAttacker){
                                main.getGame().getAttackers().remove(target);
                                main.getGame().getPlayers().remove(target);
                                if (sender == target){
                                    sender.sendMessage(config.getPluginPrefix() + "Jij bent uit de attackers gezet.");
                                } else {
                                    sender.sendMessage(config.getPluginPrefix() + target.getName() + " is uit de attackers gezet.");
                                }
                            }
                            for(Player p : main.getGame().getDefenders()){
                                if (p.getUniqueId() == target.getUniqueId()){
                                    sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Deze speler zit al bij de defenders.");
                                    return;
                                }
                            }
                            main.getGame().setMap(selected);
                            main.getGame().AddDefender(target);
                            main.getGame().AddPlayer(target);
                            target.teleport(selected.getSpawnDefenders());
                            target.setBedSpawnLocation(selected.getSpawnDefenders());
                            target.setGameMode(GameMode.SURVIVAL);
                            target.setScoreboard(main.getGame().getScoreboard());
                            if (main.getGame().isStarted()){
                                main.getGame().giveKit(target);
                            }
                            if (main.getGame().getSaveOldScoreboard() == null){
                                main.getGame().setSaveOldScoreboard(target.getScoreboard());
                            }
                            if (sender == target){
                                sender.sendMessage(config.getPluginPrefix() + "Jij bent toegevoegd aan de defenders.");
                            } else {
                                sender.sendMessage(config.getPluginPrefix() + target.getName() + " is toegevoegd aan de defenders.");
                            }
                            for(Player p : main.getGame().getPlayers()){
                                p.sendMessage(config.getPluginPrefix() + ChatColor.YELLOW + target.getName() + ChatColor.translateAlternateColorCodes('&', config.getPluginColor()) + " is nu een " + ChatColor.BLUE + "defender.");
                            }
                            break;
                        default:
                            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Attackers of defenders?");
                            break;
                    }
                    if (main.getGame().getPlayers().size() > 0 && !main.getGame().isInitialized()){
                        main.getGame().ActivateListener();
                        main.getGame().ActivatePreGameListener();
                        main.getGame().getParticleHandler().setupSpawnParticlesLocations();
                        main.getGame().getParticleHandler().spawnParticles();
                        main.getGame().setInitialized(true);
                    }
                    if (main.getGame().getPlayers().size() > 0){
                        main.getGame().getScoreboardHandler().UpdatePreGameScoreboard(-1);
                    }
                    if (main.getGame().getAttackers().size() >= 5 && main.getGame().getDefenders().size() >= 5){
                        main.getGame().Start();
                    }
                } else {
                    sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Attackers of defenders?");
                }
            } else {
                sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je hebt geen map doorgegeven.");
            }
        } else {
            sender.sendMessage(config.getPluginPrefix() + ChatColor.RED + "Je hebt geen naam doorgegeven.");
        }
    }
}
