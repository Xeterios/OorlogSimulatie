package mu.xeterios.oorlogsimulatie.game.timer;

import mu.xeterios.oorlogsimulatie.game.OS;
import mu.xeterios.oorlogsimulatie.game.timer.timers.GameTimer;
import mu.xeterios.oorlogsimulatie.game.timer.timers.StartupTimer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerHandler {

    private OS game;
    private Timer timer;

    public TimerHandler(OS game){
        this.game = game;
    }

    public TimerTask GetTimer(TimerType type) {
        switch (type) {
            case STARTUP:
                return new StartupTimer(this, game);
            case GAME:
                return new GameTimer(this, game);
        }
        return null;
    }

    public boolean RunTimer(TimerType type) {
        this.timer = new Timer();
        if (GetTimer(type) != null) {
            TimerTask task = GetTimer(type);
            timer.schedule(task, 0, 1000);
            return true;
        }
        return false;
    }

    public void StopTimer() {
        if (this.timer != null){
            this.timer.cancel();
            this.timer.purge();
            this.timer = new Timer();
        }
    }
}
