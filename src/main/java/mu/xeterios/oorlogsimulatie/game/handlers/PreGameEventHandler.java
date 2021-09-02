package mu.xeterios.oorlogsimulatie.game.handlers;

import mu.xeterios.oorlogsimulatie.game.OS;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PreGameEventHandler implements Listener {

    private final OS game;

    public PreGameEventHandler(OS game){
        this.game = game;
    }

    @EventHandler
    public void preventSpawnMovement(PlayerMoveEvent e){
        String team = "";
        for(Player p : game.getDefenders()){
            if (p.getUniqueId() == e.getPlayer().getUniqueId()){
                team = "defenders";
                break;
            }
        }
        for(Player p : game.getAttackers()){
            if (p.getUniqueId() == e.getPlayer().getUniqueId()){
                team = "attackers";
                break;
            }
        }
        double difX = 0.0;
        double difZ = 0.0;
        switch (team){
            case "attackers":
                difX = e.getTo().getBlockX() - game.getMap().getSpawnAttackers().getBlockX();
                difZ= e.getTo().getBlockZ() - game.getMap().getSpawnAttackers().getBlockZ();
                break;
            case "defenders":
                difX = e.getTo().getBlockX() - game.getMap().getSpawnDefenders().getBlockX();
                difZ= e.getTo().getBlockZ() - game.getMap().getSpawnDefenders().getBlockZ();
                break;
            default:
                break;
        }
        if (!(difX > (-1 * (game.getParticleHandler().getArea()/2)) && difX < (game.getParticleHandler().getArea()/2)) || !(difZ > (-1 * (game.getParticleHandler().getArea()/2)) && difZ < (game.getParticleHandler().getArea()/2))){
            e.getPlayer().sendTitle(ChatColor.RED + "Buiten spawn!", ChatColor.GRAY + "Je mag hier niet heen", 10, 20, 10);
            e.setCancelled(true);
        }
    }
}
