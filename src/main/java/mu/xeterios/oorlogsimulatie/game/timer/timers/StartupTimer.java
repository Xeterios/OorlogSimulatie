package mu.xeterios.oorlogsimulatie.game.timer.timers;

import mu.xeterios.oorlogsimulatie.game.OS;
import mu.xeterios.oorlogsimulatie.game.timer.TimerHandler;

import java.util.TimerTask;

public class StartupTimer extends TimerTask {

    private final TimerHandler handler;
    private final OS game;
    private int i = 10;

    public StartupTimer(TimerHandler handler, OS game){
        this.handler = handler;
        this.game = game;
    }

    @Override
    public void run() {
        if (i % 11 == 0){

        } else {
            i--;
        }
    }
}
