package mu.xeterios.oorlogsimulatie.map;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;

public class Map {

    private String mapName;
    private ProtectedRegion region;

    private Location spawnAttackers;
    private Location spawnDefenders;

    public Map(String mapName, ProtectedRegion region){
        this.mapName = mapName;
        this.region = region;
    }

    public String getMapName() {
        return mapName;
    }

    public ProtectedRegion getRegion() {
        return region;
    }

    public Location getSpawnAttackers() {
        return spawnAttackers;
    }

    public Location getSpawnDefenders() {
        return spawnDefenders;
    }

    public void setSpawnAttackers(Location spawnAttackers) {
        this.spawnAttackers = spawnAttackers;
    }

    public void setSpawnDefenders(Location spawnDefenders) {
        this.spawnDefenders = spawnDefenders;
    }

    @Override
    public String toString(){
        return region.getMinimumPoint().getX() + " " + region.getMinimumPoint().getY() + " " + region.getMinimumPoint().getZ() + " to " + region.getMaximumPoint().getX() + " " + region.getMaximumPoint().getY() + " " + region.getMaximumPoint().getZ();
    }
}
