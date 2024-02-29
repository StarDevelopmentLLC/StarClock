package example;

import com.stardevllc.starclock.ClockManager;
import com.stardevllc.starclock.clocks.Timer;
import com.stardevllc.starlib.time.TimeUnit;

import java.util.TimerTask;

public class StarClockExampe {
    public static void main(String[] args) {
        ClockManager clockManager = new ClockManager(null, 1);
        
        java.util.Timer javaTimer = new java.util.Timer();
        javaTimer.schedule(new TimerTask() {
            public void run() {
                clockManager.getRunnable().run();
            }
        }, 1L, 1L);
        
        Timer timer = clockManager.createTimer(TimeUnit.SECONDS.toMillis(15));
        timer.addRepeatingCallback(snapshot -> System.out.println("Remaining Time: " + TimeUnit.SECONDS.fromMillis(snapshot.getTime()) + "s"), TimeUnit.SECONDS, 1);
        timer.addCallback(snapshot -> System.out.println("5 seconds remain!"), TimeUnit.SECONDS, 5);
        timer.start();
    }
}
