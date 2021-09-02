package mu.xeterios.oorlogsimulatie.game;

import lombok.Getter;
import lombok.Setter;
import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.game.handlers.EventHandlers;
import mu.xeterios.oorlogsimulatie.game.handlers.PreGameEventHandler;
import mu.xeterios.oorlogsimulatie.game.handlers.ScoreboardHandler;
import mu.xeterios.oorlogsimulatie.game.timer.TimerHandler;
import mu.xeterios.oorlogsimulatie.game.timer.TimerType;
import mu.xeterios.oorlogsimulatie.game.handlers.ParticleHandler;
import mu.xeterios.oorlogsimulatie.map.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.Objects;

public class OS {

    @Getter private final ArrayList<Player> players;
    @Getter private final ArrayList<Player> attackers;
    @Getter private final ArrayList<Player> defenders;
    @Getter private ParticleHandler particleHandler;
    @Getter @Setter private boolean started;
    @Getter @Setter private boolean initialized;

    @Getter private Scoreboard scoreboard;
    @Getter @Setter private Scoreboard saveOldScoreboard;
    @Getter private Objective objective;

    private TimerHandler handler;
    @Getter private EventHandlers handlers;
    @Getter private PreGameEventHandler preGameEventHandlers;
    @Getter private ScoreboardHandler scoreboardHandler;

    @Getter @Setter private Map map;

    public OS() {
        this.players = new ArrayList<>();
        this.attackers = new ArrayList<>();
        this.defenders = new ArrayList<>();
        this.handler = new TimerHandler(this);
        this.particleHandler = new ParticleHandler(this);
        this.handlers = new EventHandlers(this);
        this.preGameEventHandlers = new PreGameEventHandler(this);
        this.scoreboardHandler = new ScoreboardHandler(this);
        this.initialized = false;
        Setup();
    }

    private void Setup(){
        try {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            this.saveOldScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
            if (scoreboard.getTeam("Attackers") != null){
                Objects.requireNonNull(scoreboard.getTeam("Attackers")).unregister();
            }
            Team att = scoreboard.registerNewTeam("Attackers");
            att.setPrefix(ChatColor.RED + "");
            att.setColor(ChatColor.RED);

            if (scoreboard.getTeam("Defenders") != null){
                Objects.requireNonNull(scoreboard.getTeam("Defenders")).unregister();
            }
            Team def = scoreboard.registerNewTeam("Defenders");
            def.setPrefix(ChatColor.BLUE + "");
            def.setColor(ChatColor.BLUE);

            this.objective = scoreboard.registerNewObjective("Oorlog", "dummy");
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', Main.getPlugin(Main.class).GetConfig().getPluginColor()) + "" + ChatColor.BOLD + "Oorlog");
        } catch (Exception e){
            this.objective = null;
            this.scoreboard = null;
        }
    }

    public boolean Start(){
        if (!started){
            if (defenders.size() == 0 || attackers.size() == 0){
                return false;
            }
            // Setup
            this.started = true;
            return handler.RunTimer(TimerType.STARTUP);
        } else {
            return false;
        }
    }

    public void Stop(){
        if (started){
            for (Player p : players){
                p.sendTitle(ChatColor.YELLOW + "" +  ChatColor.BOLD + "Klaar!", ChatColor.WHITE + "Het spel is afgelopen!", 10, 40, 10);
                p.setScoreboard(saveOldScoreboard);
                p.setBedSpawnLocation(map.getSpawnAttackers().getWorld().getSpawnLocation());
            }
            this.handler.StopTimer();
            this.players.clear();
            this.attackers.clear();
            this.defenders.clear();
            this.initialized = false;
            this.started = false;
            this.particleHandler.disableParticles();
            HandlerList.unregisterAll(Main.getPlugin(Main.class));
            this.handler = new TimerHandler(this);
            this.particleHandler = new ParticleHandler(this);
            this.handlers = new EventHandlers(this);
            this.preGameEventHandlers = new PreGameEventHandler(this);
        }
    }

    public void AddAttacker(Player p){
        Objects.requireNonNull(scoreboard.getTeam("Attackers")).addEntry(p.getName());
        p.setScoreboard(scoreboard);
        this.attackers.add(p);
    }

    public void AddDefender(Player p){
        Objects.requireNonNull(scoreboard.getTeam("Defenders")).addEntry(p.getName());
        p.setScoreboard(scoreboard);
        this.defenders.add(p);
    }

    public void RemoveAttacker(Player p){
        Objects.requireNonNull(scoreboard.getTeam("Attackers")).removeEntry(p.getName());
        p.setScoreboard(saveOldScoreboard);
        this.attackers.remove(p);
    }

    public void RemoveDefender(Player p){
        Objects.requireNonNull(scoreboard.getTeam("Defenders")).removeEntry(p.getName());
        p.setScoreboard(saveOldScoreboard);
        this.defenders.remove(p);
    }

    public void AddPlayer(Player p){
        this.players.add(p);
    }

    public void ActivateListener(){
        Bukkit.getPluginManager().registerEvents(handlers, Main.getPlugin(Main.class));
    }

    public void ActivatePreGameListener(){
        Bukkit.getPluginManager().registerEvents(preGameEventHandlers, Main.getPlugin(Main.class));
    }

    public void StopListener(){
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> HandlerList.unregisterAll(handlers), 20L);
    }
}
