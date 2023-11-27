package com.stardevllc.starclock.snapshot;

import com.stardevllc.starclock.clocks.Stopwatch;

/**
 * The snapshot corresponding to {@link Stopwatch}'s <br>
 * Please see {@link ClockSnapshot} for more information
 */
public class StopwatchSnapshot extends ClockSnapshot {
    
    private long endTime;
    
    public StopwatchSnapshot(long time, long endTime, boolean paused, long countAmount) {
        super(time, paused, countAmount);
        this.endTime = endTime;
    }
    
    /**
     * @return The end time of the stopwatch at the time of snapshot creation.
     */
    public long getEndTime() {
        return endTime;
    }
}
