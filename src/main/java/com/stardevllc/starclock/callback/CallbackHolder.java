package com.stardevllc.starclock.callback;

import com.stardevllc.starclock.snapshot.ClockSnapshot;

import java.util.UUID;

/**
 * This class is mainly for internal use, however, this class is used for some checks and logic for Clocks <br>
 * @param <T> The Snapshot type
 */
public class CallbackHolder<T extends ClockSnapshot> {
    protected final ClockCallback<T> callback;
    protected final UUID callbackId;
    protected final long period;
    protected boolean repeating = true;
    protected long lastRun = -1;
    
    /**
     * Constructs a new CallbackHolder
     * @param callback The callback reference
     * @param callbackId The ID of the callback
     * @param period The interval that the callback runs at in milliseconds
     */
    public CallbackHolder(ClockCallback<T> callback, UUID callbackId, long period) {
        this.callback = callback;
        this.callbackId = callbackId;
        this.period = period;
    }

    public CallbackHolder(ClockCallback<T> callback, UUID callbackId, long period, boolean repeating) {
        this.callback = callback;
        this.callbackId = callbackId;
        this.period = period;
        this.repeating = repeating;
    }

    /**
     * @return The callback instance
     */
    public ClockCallback<T> getCallback() {
        return callback;
    }
    
    /**
     * @return The period in milliseconds
     */
    public long getPeriod() {
        return period;
    }
    
    /**
     * @return The last time that the callback ran based on the clock's time, in milliseconds
     */
    public long getLastRun() {
        return lastRun;
    }
    
    /**
     * Sets the last run time of the clock, based on the clock's time
     * @param lastRun The new last run time
     */
    public void setLastRun(long lastRun) {
        this.lastRun = lastRun;
    }

    public boolean isRepeating() {
        return repeating;
    }
}