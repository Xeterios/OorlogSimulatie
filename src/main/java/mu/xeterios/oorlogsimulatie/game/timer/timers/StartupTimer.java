package mu.xeterios.oorlogsimulatie.game.timer.timers;

import mu.xeterios.oorlogsimulatie.game.OS;
import mu.xeterios.oorlogsimulatie.game.handlers.PreGameEventHandler;
import mu.xeterios.oorlogsimulatie.game.timer.TimerHandler;
import mu.xeterios.oorlogsimulatie.game.timer.TimerType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scoreboard.Score;

import java.util.TimerTask;

public class StartupTimer extends TimerTask {

    private final TimerHandler handler;
    private final OS game;
    private int i = 60;

    public StartupTimer(TimerHandler handler, OS game){
        this.handler = handler;
        this.game = game;
    }

    @Override
    public void run() {
        if (i % 61 == 0){
            this.cancel();
            game.getParticleHandler().releaseParticles();
            handler.StopTimer();
            handler.RunTimer(TimerType.GAME);
            game.setInitialized(false);
            HandlerList.unregisterAll(game.getPreGameEventHandlers());
            for(Player p : game.getPlayers()){
                p.sendTitle(ChatColor.GREEN + "Start!", "", 0, 20, 10);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 15, 1);
            }
            game.getScoreboardHandler().ResetScoreboard();
        } else {
            ChatColor titleColor = null;
            if (i >= 0 && i < 5){
                titleColor = ChatColor.RED;
            }
            if (i >= 5 && i < 15){
                titleColor = ChatColor.YELLOW;
            }
            if (i >= 15 && i <= 60){
                titleColor = ChatColor.GREEN;
            }
            if ((i > 0 && i <= 5) || i == 10 || i == 15 || i == 30 || i == 45 || i == 60){
                for(Player p : game.getPlayers()){
                    p.sendTitle("" + titleColor + i, "", 0, 40, 10);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 15, 0);
                }
            }
            this.game.getScoreboardHandler().UpdatePreGameScoreboard(i);
            i--;
        }
    }
}
