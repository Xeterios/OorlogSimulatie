package mu.xeterios.oorlogsimulatie.game.timer.timers;

import mu.xeterios.oorlogsimulatie.game.OS;
import mu.xeterios.oorlogsimulatie.game.timer.TimerHandler;

import java.util.TimerTask;

public class EndingTimer extends TimerTask {

    private final TimerHandler handler;
    private final OS game;

    public EndingTimer(TimerHandler handler, OS game){
        this.handler = handler;
        this.game = game;
    }

    @Override
    public void run() {

    }
}
