package com.stardevllc.starclock.clocks;

import com.stardevllc.starclock.Clock;
import com.stardevllc.starclock.ClockLongProperty;
import com.stardevllc.starclock.callback.CallbackHolder;
import com.stardevllc.starclock.snapshot.StopwatchSnapshot;
import com.stardevllc.starlib.observable.property.LongProperty;

/**
 * This is your typical count-up type of clock. <br>
 * The {@link Clock#addTime(long)} adds time as if it had counted that time <br>
 * The {@link Clock#removeTime(long)} method removes time as if it had not counted that amount of time. <br>
 * The {@link Clock#setTime(long)} method sets the current time and the stopwatch will count-up starting with the new value.
 */
public class Stopwatch extends Clock<StopwatchSnapshot> {
    protected LongProperty endTimeProperty = new ClockLongProperty();
    
    /**
     * Constructs a new Stopwatch
     * @param endTime The end time of the stopwatch. This is how many milliseconds that the clock should count to, and not the timestamp
     * @param countAmount The count amount. See the {@link Clock} documentation for how this works
     */
    public Stopwatch(long endTime, long countAmount) {
        super(0L, countAmount);
        this.endTimeProperty.setValue(endTime);
        this.endTimeProperty.addChangeListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                unpause();
            }
        });
    }

    @Override
    protected boolean shouldCallback(CallbackHolder<StopwatchSnapshot> holder) {
        long lastRun = holder.getLastRun(); //Variable to easily access when it ran last
        if (!holder.isRepeating()) {
            long run = holder.getPeriod(); //This is for when the non-repeating callback should run based on the length
            return lastRun == -1 && this.getTime() >= run;
        } else {
            if (holder.getLastRun() == -1) {
                return holder.getPeriod() >= this.getTime();
            } else {
                long nextRun = holder.getLastRun() + holder.getPeriod();
                return this.getTime() >= nextRun;
            }
        }
    }

    @Override
    public StopwatchSnapshot createSnapshot() {
        return new StopwatchSnapshot(getTime(), getEndTime(), paused, getCountAmount());
    }

    @Override
    protected void count() {
        this.timeProperty.setValue(Math.min(getTime() + countAmount, this.getEndTime()));
    }

    @Override
    public Stopwatch start() {
        return (Stopwatch) super.start();
    }
    
    /**
     * @return The time that this clock will end at. This will simply stop calling the Callbacks until the time value is changed to be lower
     */
    public long getEndTime() {
        return endTimeProperty.get();
    }
    
    /**
     * Sets the new endTime of this Stopwatch. <br>
     * If the new endTime is less that the previous one, this does nothing. If it is more, the Stopwatch will continue
     * @param endTime The new endTime
     */
    public void setEndTime(long endTime) {
        this.endTimeProperty.setValue(endTime);
    }
    
    public LongProperty endTimeProperty() {
        return this.endTimeProperty;
    }
}