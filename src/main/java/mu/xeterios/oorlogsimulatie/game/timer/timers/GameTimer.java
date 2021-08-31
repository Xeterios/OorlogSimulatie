package mu.xeterios.oorlogsimulatie.game.timer.timers;

import mu.xeterios.oorlogsimulatie.game.OS;
import mu.xeterios.oorlogsimulatie.game.timer.TimerHandler;

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
            game.Stop();
        } else {
            if (game.getPlayers().size() == 0){
                game.Stop();
            }
        }
    }
}
