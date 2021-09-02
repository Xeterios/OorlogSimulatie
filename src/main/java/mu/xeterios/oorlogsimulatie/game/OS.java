package mu.xeterios.oorlogsimulatie.game;

import mu.xeterios.oorlogsimulatie.game.timer.TimerHandler;
import mu.xeterios.oorlogsimulatie.game.timer.TimerType;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class OS {

    private final ArrayList<Player> players;
    private final ArrayList<Player> attackers;
    private final ArrayList<Player> defenders;
    private final TimerHandler handler;
    private boolean started;

    private Map map;
    private World world;

    public OS() {
        this.players = new ArrayList<>();
        this.attackers = new ArrayList<>();
        this.defenders = new ArrayList<>();
        this.handler = new TimerHandler(this);
    }

    public boolean Start(String[] arg){
        if (!started){
            if (Bukkit.getWorld(arg[1]) == null){
                return false;
            }
            this.world = Bukkit.getWorld(arg[1]);
            assert this.world != null;
            players.addAll(this.world.getPlayers());
            this.started = true;

            // Setup

            return handler.RunTimer(TimerType.STARTUP);
        } else {
            return false;
        }
    }

    public void Stop(){
        if (started){
            this.players.clear();
        }
    }

    public void AddAttacker(Player p){
        this.attackers.add(p);
    }

    public void AddDefenders(Player p){
        this.defenders.add(p);
    }

    public World getWorld() {
        return world;
    }

    public void setMap(Map map){
        this.map = map;
    }
    
    public Map getMap(){
        return this.map;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getAttackers() {
        return attackers;
    }

    public ArrayList<Player> getDefenders() {
        return defenders;
    }
}
