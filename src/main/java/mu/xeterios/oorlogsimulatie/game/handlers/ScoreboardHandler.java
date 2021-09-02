package mu.xeterios.oorlogsimulatie.game.handlers;

import mu.xeterios.oorlogsimulatie.Main;
import mu.xeterios.oorlogsimulatie.game.OS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;

public class ScoreboardHandler {

    private OS game;

    public ScoreboardHandler(OS game){
        this.game = game;
    }

    public void UpdatePreGameScoreboard(int time){
        String timeFormatPlusOne = GetTimeFormat(time+1);
        String timeFormat = GetTimeFormat(time);

        this.game.getScoreboard().resetScores(ChatColor.GRAY + "Simulatie start over: " + ChatColor.RED + timeFormatPlusOne);
        this.game.getScoreboard().resetScores(ChatColor.GRAY + "Wachten op spelers...");
        this.game.getScoreboard().resetScores(ChatColor.RED + "  Attackers " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + (this.game.getAttackers().size()-1));
        this.game.getScoreboard().resetScores(ChatColor.BLUE + "  Defenders " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + (this.game.getDefenders().size()-1));
        this.game.getScoreboard().resetScores(ChatColor.RED + "  Attackers " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + (this.game.getAttackers().size()+1));
        this.game.getScoreboard().resetScores(ChatColor.BLUE + "  Defenders " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + (this.game.getDefenders().size()+1));

        Score row1 = this.game.getObjective().getScore(" ");
        row1.setScore(15);
        Score row2 = this.game.getObjective().getScore(ChatColor.GRAY + "Teams " + ChatColor.DARK_GRAY + " » ");
        row2.setScore(14);
        Score row3 = this.game.getObjective().getScore(ChatColor.RED + "  Attackers " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + this.game.getAttackers().size());
        row3.setScore(13);
        Score row4 = this.game.getObjective().getScore(ChatColor.BLUE + "  Defenders " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + this.game.getDefenders().size());
        row4.setScore(13);
        Score row5 = this.game.getObjective().getScore("  ");
        row5.setScore(12);
        if (time != -1){
            Score row6 = this.game.getObjective().getScore(ChatColor.GRAY + "Simulatie start over: " + ChatColor.RED + timeFormat);
            row6.setScore(11);
        } else {
            Score row6 = this.game.getObjective().getScore(ChatColor.GRAY + "Wachten op spelers...");
            row6.setScore(11);
        }
        Score row7 = this.game.getObjective().getScore("   ");
        row7.setScore(10);
    }

    public void UpdateGameScoreboard(int time){
        String timeFormatPlusOne = GetTimeFormat(time+1);
        String timeFormat = GetTimeFormat(time);
        //Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> this.game.getScoreboard().resetScores(ChatColor.GRAY + "Tijd over: " + ChatColor.RED + GetTimeFormat(time+1)));
        this.game.getScoreboard().resetScores(ChatColor.RED + "  Attackers " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + (this.game.getAttackers().size()+1));
        this.game.getScoreboard().resetScores(ChatColor.BLUE + "  Defenders " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + (this.game.getDefenders().size()+1));

        Score row1 = this.game.getObjective().getScore(" ");
        row1.setScore(15);
        Score row2 = this.game.getObjective().getScore(ChatColor.GRAY + "Teams " + ChatColor.DARK_GRAY + " » ");
        row2.setScore(14);
        Score row3 = this.game.getObjective().getScore(ChatColor.RED + "  Attackers " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + this.game.getAttackers().size());
        row3.setScore(13);
        Score row4 = this.game.getObjective().getScore(ChatColor.BLUE + "  Defenders " + ChatColor.DARK_GRAY + " » " + ChatColor.GRAY + this.game.getDefenders().size());
        row4.setScore(13);
        Score row5 = this.game.getObjective().getScore("  ");
        row5.setScore(12);
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), () -> {
            this.game.getScoreboard().resetScores(ChatColor.GRAY + "Tijd over: " + ChatColor.RED + timeFormatPlusOne);
            Score row6 = this.game.getObjective().getScore(ChatColor.GRAY + "Tijd over: " + ChatColor.RED + timeFormat);
            row6.setScore(11);
        }, 1L);
        Score row7 = this.game.getObjective().getScore("   ");
        row7.setScore(10);
    }

    private String GetTimeFormat(int time){
        int minutes = time / 60;
        int seconds = time % 60;
        String sMinutes = String.format("%02d", minutes);
        String sSeconds = String.format("%02d", seconds);
        return sMinutes + ":" + sSeconds;
    }

    public void ResetScoreboard(){
        ArrayList<String> list = new ArrayList<>(game.getScoreboard().getEntries());
        for(String s : list){
            game.getScoreboard().resetScores(s);
        }
    }
}
