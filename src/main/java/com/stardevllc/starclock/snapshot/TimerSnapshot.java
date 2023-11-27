package com.stardevllc.starclock.snapshot;


import com.stardevllc.starclock.clocks.Timer;

/**
 * The snapshot corresponding to {@link Timer}'s <br>
 * Please see {@link ClockSnapshot} for more information
 */
public class TimerSnapshot extends ClockSnapshot {
    
    protected final long length;
    
    public TimerSnapshot(long time, boolean paused, long length, long countAmount) {
        super(time, paused, countAmount);
        this.length = length;
    }
    
    /**
     * @return The length of the clock at the snapshot
     */
    public long getLength() {
        return length;
    }
}
