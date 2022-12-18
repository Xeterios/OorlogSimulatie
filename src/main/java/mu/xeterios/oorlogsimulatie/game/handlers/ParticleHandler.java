package mu.xeterios.oorlogsimulatie.game.handlers;

import lombok.Getter;
import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.game.OS;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class ParticleHandler {

    private Map<Location, Particle.DustOptions> spawnParticlesLocations;
    private BukkitTask spawnParticles;
    private final OS game;

    @Getter double area = 11.0;
    @Getter int occurence = (int) (area*5);
    @Getter float size = 0.5F;

    public ParticleHandler(OS game){
        this.game = game;
        this.spawnParticlesLocations = new HashMap<>();
    }

    public void setupSpawnParticlesLocations(){
        this.spawnParticlesLocations = new HashMap<>();
        Particle.DustOptions attOpt = new Particle.DustOptions(Color.RED, size);
        Location locAtt = new Location(game.getMap().getSpawnAttackers().getWorld(), game.getMap().getSpawnAttackers().getBlockX()+0.5, game.getMap().getSpawnAttackers().getBlockY(), game.getMap().getSpawnAttackers().getBlockZ()+0.5);
        locAtt.add(area/2, 0, area/2);
        double toMoveAtt;
        for (int i = 0; i <= 4*occurence; i++){
            if (i > 0 && i <= occurence){
                toMoveAtt = -1 * (area / occurence);
                locAtt.add(toMoveAtt, 0, 0);
                spawnParticlesLocations.put(new Location(locAtt.getWorld(), locAtt.getX(), locAtt.getY(), locAtt.getZ()), attOpt);
            }
            if (i > occurence && i <= 2*occurence){
                toMoveAtt = -1 * (area / occurence);
                locAtt.add(0, 0, toMoveAtt);
                spawnParticlesLocations.put(new Location(locAtt.getWorld(), locAtt.getX(), locAtt.getY(), locAtt.getZ()), attOpt);
            }
            if (i > 2*occurence && i <= 3*occurence){
                toMoveAtt = area / occurence;
                locAtt.add(toMoveAtt, 0, 0);
                spawnParticlesLocations.put(new Location(locAtt.getWorld(), locAtt.getX(), locAtt.getY(), locAtt.getZ()), attOpt);
            }
            if (i > 3*occurence){
                toMoveAtt = area / occurence;
                locAtt.add(0, 0, toMoveAtt);
                spawnParticlesLocations.put(new Location(locAtt.getWorld(), locAtt.getX(), locAtt.getY(), locAtt.getZ()), attOpt);
            }
        }

        Particle.DustOptions defOpt = new Particle.DustOptions(Color.BLUE, size);
        Location locDef = new Location(game.getMap().getSpawnDefenders().getWorld(), game.getMap().getSpawnDefenders().getBlockX()+0.5, game.getMap().getSpawnDefenders().getBlockY(), game.getMap().getSpawnDefenders().getBlockZ()+0.5);
        locDef.add(area/2, 0, area/2);
        double toMoveDef;
        for (int j = 0; j <= 4*occurence; j++){
            if (j > 0 && j <= occurence){
                toMoveDef = -1 * (area / occurence);
                locDef.add(toMoveDef, 0, 0);
                spawnParticlesLocations.put(new Location(locDef.getWorld(), locDef.getX(), locDef.getY(), locDef.getZ()), defOpt);
            }
            if (j > occurence && j <= 2*occurence){
                toMoveDef = -1 * (area / occurence);
                locDef.add(0, 0, toMoveDef);
                spawnParticlesLocations.put(new Location(locDef.getWorld(), locDef.getX(), locDef.getY(), locDef.getZ()), defOpt);
            }
            if (j > 2*occurence && j <= 3*occurence){
                toMoveDef = area / occurence;
                locDef.add(toMoveDef, 0, 0);
                spawnParticlesLocations.put(new Location(locDef.getWorld(), locDef.getX(), locDef.getY(), locDef.getZ()), defOpt);
            }
            if (j > 3*occurence){
                toMoveDef = area / occurence;
                locDef.add(0, 0, toMoveDef);
                spawnParticlesLocations.put(new Location(locDef.getWorld(), locDef.getX(), locDef.getY(), locDef.getZ()), defOpt);
            }
        }
    }

    public void spawnParticles(){
        this.spawnParticles = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(Main.class), () -> {
            if (this.game.isInitialized()){
                for(Location loc : spawnParticlesLocations.keySet()){
                    loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, 0, 0, 0, 2, spawnParticlesLocations.get(loc));
                }
            }
        }, 0, 1);
    }

    public void releaseParticles(){
        if (this.game.isInitialized()){
            for(Location loc : spawnParticlesLocations.keySet()){
                loc.getWorld().spawnParticle(Particle.CLOUD, loc, 1, 0, 0, 0, 0.1);
            }
        }
        this.spawnParticlesLocations = new HashMap<>();
    }

    public void disableParticles(){
        if (this.spawnParticles != null){
            if (!this.spawnParticles.isCancelled()){
                this.spawnParticles.cancel();
            }
        }
    }
}
