package mu.xeterios.oorlogsimulatie.game.handlers;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.game.OS;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

public class EventHandlers implements Listener {

    private final OS game;

    public EventHandlers(OS game){
        this.game = game;
    }

    @EventHandler
    public void blockPlacement(BlockPlaceEvent e){
        e.getPlayer().sendMessage(Main.getPlugin(Main.class).GetConfig().getPluginPrefix() + ChatColor.RED + "Je mag hier niet bouwen!");
        e.setCancelled(true);
    }

    @EventHandler
    public void waterPlacement(PlayerBucketEmptyEvent e){
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
            game.RemoveAttacker(target);
            game.RemoveDefender(target);
            target.setGameMode(GameMode.SPECTATOR);
            if (game.getDefenders().size() == 0 || game.getAttackers().size() == 0){
                game.Stop();
            }
        }
    }
}
