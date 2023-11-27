package com.stardevllc.starclock.condition.defaults;

import com.stardevllc.starclock.clocks.Timer;
import com.stardevllc.starclock.condition.ClockEndCondition;
import com.stardevllc.starclock.snapshot.TimerSnapshot;

/**
 * Default implementation for the {@link Timer} ending based on if the time hits 0. <br>
 * This is not applied by default to Timers and is provided as a default implementation for easier use
 */
public class TimerEndCondition implements ClockEndCondition<TimerSnapshot> {
    @Override
    public boolean shouldEnd(TimerSnapshot snapshot) {
        return snapshot.getTime() <= 0;
    }
}
