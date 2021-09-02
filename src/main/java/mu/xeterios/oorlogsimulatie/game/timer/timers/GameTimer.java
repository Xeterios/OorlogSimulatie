package mu.xeterios.oorlogsimulatie.game.timer.timers;

import mu.xeterios.oorlogsimulatie.game.OS;
import mu.xeterios.oorlogsimulatie.game.timer.TimerHandler;
import mu.xeterios.oorlogsimulatie.game.timer.TimerType;

import java.util.TimerTask;

public class GameTimer extends TimerTask {

    private final TimerHandler handler;
    private final OS game;
    public int i = 2700;

    public GameTimer(TimerHandler handler, OS game){
        this.handler = handler;
        this.game = game;
    }

    @Override
    public void run() {
        if (i % (i+1) == 0){
            this.cancel();
            handler.StopTimer();
            handler.RunTimer(TimerType.ENDING);
            game.getScoreboardHandler().ResetScoreboard();
        } else {
            if (game.getDefenders().size() == 0 || game.getAttackers().size() == 0){
                this.cancel();
                handler.StopTimer();
                handler.RunTimer(TimerType.ENDING);
            }
            this.game.getScoreboardHandler().UpdateGameScoreboard(i);
            i--;
        }
    }
}
