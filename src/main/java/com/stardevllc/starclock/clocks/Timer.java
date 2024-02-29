package com.stardevllc.starclock.clocks;

import com.stardevllc.starclock.Clock;
import com.stardevllc.starclock.ClockLongProperty;
import com.stardevllc.starclock.callback.CallbackHolder;
import com.stardevllc.starclock.snapshot.TimerSnapshot;
import com.stardevllc.starlib.observable.property.LongProperty;

/**
 * This is your typical count-down type of clock. <br>
 * The {@link Clock#addTime(long)} adds time as if it had not counted that time, keeping the previous elapsed time <br>
 * The {@link Clock#removeTime(long)} method removes time as if it had counted that amount of time. <br>
 * The {@link Clock#setTime(long)} method sets the current time and the timer will count-down starting with the new value. <br>
 * There are some new methods for manipulating time, please see that documentation
 */
public class Timer extends Clock<TimerSnapshot> {
    
    protected LongProperty lengthProperty = new ClockLongProperty();
    
    /**
     * Constructs a new Timer
     * @param length The starting length of the timer
     * @param countAmount The count amount. See the {@link Clock} documentation for how this works
     */
    public Timer(long length, long countAmount) {
        super(length, countAmount);
        this.lengthProperty.setValue(length);
        this.lengthProperty.addChangeListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                unpause();
            }
        });
    }

    @Override
    protected boolean shouldCallback(CallbackHolder<TimerSnapshot> holder) {
        long lastRun = holder.getLastRun(); //Variable to easily access when it ran last
        if (!holder.isRepeating()) {
            long run = holder.getPeriod(); //This is for when the non-repeating callback should run based on the length
            return lastRun == -1 && this.getTime() <= run;
        } else {
            if (holder.getLastRun() == -1) {
                return holder.getPeriod() <= this.getTime();
            } else {
                long nextRun = holder.getLastRun() - holder.getPeriod();
                return this.getTime() <= nextRun;
            }
        }
    }

    @Override
    protected void count() {
        this.timeProperty.setValue(Math.max(0, getTime() - countAmount));
    }

    /**
     * @return The length of this Timer
     */
    public long getLength() {
        return lengthProperty.get();
    }
    
    /**
     * Resets the current count back to the length
     */
    public void reset() {
        setTime(getLength());
        this.callbacks.values().forEach(callback -> callback.setLastRun(-1));
    }
    
    /**
     * Sets the new length of this timer, keeping previous elapsed time. Will not go below 0 though <br>
     * If you want it to start over, use the setLengthAndReset() method, which does not preserve elapsed time.
     * @param length The new length
     */
    public void setLength(long length) {
        long elapsed = this.getLength() - this.getTime();
        this.lengthProperty.setValue(length);
        this.timeProperty.setValue(Math.max(this.getLength() - elapsed, 0));
        this.callbacks.values().forEach(callback -> {
            if (callback.isRepeating()) {
                callback.setLastRun(-1); //Reset last run if it is a repeating one
            } else {
                if (callback.getLastRun() != -1) {
                    if (this.getLength() - callback.getPeriod() > callback.getLastRun()) {
                        callback.setLastRun(-1); //This reset the non-repeating callbacks if the new length - the interval is greater th an the last run
                    }
                }
            }
        });
    }
    
    /**
     * Sets the new length and resets the time to the new length as well, loosing the elapsed time.
     * @param length The new length
     */
    public void setLengthAndReset(long length) {
        this.lengthProperty.setValue(length);
        this.reset();
    }
    
    /**
     * Adds length to this Timer. This will preserve elapsed time.
     * @param length The length to add
     */
    public void addLength(long length) {
        setLength(this.getLength() + length);
    }
    
    /**
     * Removes length from this Timer. This will preserve elapsed time.
     * @param length The length to remove
     */
    public void removeLength(long length) {
        if (length - this.getLength() < 0) {
            length = this.getLength();
        }
        
        setLength(this.getLength() - length);
    }
    
    @Override
    public Timer start() {
        return (Timer) super.start();
    }
    
    @Override
    public TimerSnapshot createSnapshot() {
        return new TimerSnapshot(this.getTime(), this.paused, this.getLength(), getCountAmount());
    }
    
    public LongProperty lengthProperty() {
        return this.lengthProperty;
    }
}