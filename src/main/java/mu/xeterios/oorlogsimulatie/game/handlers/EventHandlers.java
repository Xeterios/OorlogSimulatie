package mu.xeterios.oorlogsimulatie.game.handlers;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.game.OS;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class EventHandlers implements Listener {

    private final OS game;

    public EventHandlers(OS game){
        this.game = game;
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e){
        e.getPlayer().sendMessage(Main.getPlugin(Main.class).GetConfig().getPluginPrefix() + ChatColor.RED + "Je mag hier niet slopen!");
        e.setCancelled(true);
    }

    @EventHandler
    public void blockPlacement(BlockPlaceEvent e){
        e.getPlayer().sendMessage(Main.getPlugin(Main.class).GetConfig().getPluginPrefix() + ChatColor.RED + "Je mag hier niet bouwen!");
        e.setCancelled(true);
    }

    @EventHandler
    public void waterPlacement(PlayerBucketEmptyEvent e){
        if (!e.getBlock().isEmpty()){
            e.setCancelled(true);
            return;
        }
        Material mat = e.getBucket();
        Location loc = e.getBlock().getLocation();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> loc.getBlock().setType(Material.AIR), 5L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
            for(ItemStack i : e.getPlayer().getInventory()){
                if (i != null){
                    if (i.getType().equals(Material.BUCKET)){
                        i.setType(mat);
                    }
                }
            }
        }, 5L);
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        if (game.isInitialized()){
            e.setCancelled(true);
            return;
        }
        if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player){
            if (((Arrow) e.getDamager()).getShooter() == e.getEntity()){
                e.setCancelled(true);
                e.getDamager().remove();
                return;
            }
            if ((game.isAttacker((Player) ((Arrow) e.getDamager()).getShooter()) && game.isAttacker((Player) e.getEntity())) || (game.isDefender((Player) ((Arrow) e.getDamager()).getShooter()) && game.isDefender((Player) e.getEntity()))){
                e.setCancelled(true);
            }
        } else if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            if ((game.isAttacker((Player) e.getDamager()) && game.isAttacker((Player) e.getEntity())) || (game.isDefender((Player) e.getDamager()) && game.isDefender((Player) e.getEntity()))){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player target = null;
        for(Player p : game.getAttackers()){
            if (p.getUniqueId() == e.getEntity().getUniqueId()){
                target = p;
            }
        }
        for(Player p : game.getDefenders()){
            if (p.getUniqueId() == e.getEntity().getUniqueId()){
                target = p;
            }
        }
        if (target != null){
            e.getDrops().clear();
            e.setDeathMessage(Main.getPlugin(Main.class).GetConfig().getPluginPrefix() + game.getTeamColor(target) + Objects.requireNonNull(target.getPlayer()).getName() + ChatColor.translateAlternateColorCodes('&', "&x&C&0&C&0&C&0") + " was gedood door " + game.getTeamColor(target.getKiller()) + Objects.requireNonNull(target.getKiller()).getName());
            game.RemoveAttacker(target);
            game.RemoveDefender(target);
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String command = "lp user " + target.getName() + " permission set os.queued false";
            Bukkit.dispatchCommand(console, command);
            String command2 = "spawn " + target.getName();
            Bukkit.dispatchCommand(console, command2);
            if (game.getDefenders().size() == 0 || game.getAttackers().size() == 0){
                game.Stop();
            }
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player target = null;
        for(Player p : game.getAttackers()){
            if (p.getUniqueId() == e.getPlayer().getUniqueId()){
                target = p;
            }
        }
        for(Player p : game.getDefenders()){
            if (p.getUniqueId() == e.getPlayer().getUniqueId()){
                target = p;
            }
        }
        if (target != null){
            target.getInventory().clear();
            game.RemoveAttacker(target);
            game.RemoveDefender(target);
            e.setQuitMessage(Main.getPlugin(Main.class).GetConfig().getPluginPrefix() + ChatColor.RED + target.getName() + " heeft het spel verlaten, dus is hij uit zijn team gezet.");
            if (game.getDefenders().size() == 0 || game.getAttackers().size() == 0){
                game.Stop();
            }
        }
    }

    @EventHandler
    public void onArmorStandInteract(PlayerArmorStandManipulateEvent e){
        e.getPlayer().sendMessage(Main.getPlugin(Main.class).GetConfig().getPluginPrefix() + ChatColor.RED + "Je mag dit niet doen!");
        e.setCancelled(true);
    }
}
