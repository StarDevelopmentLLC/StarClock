package com.stardevllc.starclock;

import com.stardevllc.starclock.callback.CallbackHolder;
import com.stardevllc.starclock.callback.ClockCallback;
import com.stardevllc.starclock.condition.ClockEndCondition;
import com.stardevllc.starclock.snapshot.ClockSnapshot;
import com.stardevllc.starlib.observable.property.LongProperty;
import com.stardevllc.starlib.time.TimeUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This is the parent class for all Clocks and provides the main functionality and definitions for this Library <br>
 * The implementation classes handle the math related to the direction of time for this clock. 
 * @param <T> The type for the ClockSnapshot instance
 */
public abstract class Clock<T extends ClockSnapshot> {
    protected LongProperty timeProperty = new ClockLongProperty();
    protected boolean paused = true;
    protected boolean cancelled;
    protected Map<UUID, CallbackHolder<T>> callbacks = new HashMap<>();
    protected final long countAmount;
    protected ClockEndCondition<T> endCondition;
    
    /**
     * Constructs a new Clock
     * @param time The time to start at. This value changes as the clock progresses
     * @param countAmount The amount to count by. This is provided by default using the factory methods in the {@link ClockManager} class and is cached for easier use.
     */
    public Clock(long time, long countAmount) {
        this.timeProperty.setValue(time);
        this.countAmount = countAmount;
        
        this.timeProperty.addChangeListener((observable, oldValue, newValue) -> {
            if (!oldValue.equals(newValue)) {
                unpause();
            }
        });
    }

    /**
     * This is the logic for when the clock "ticks", which means when it counts up or down and when callbacks are checked.
     */
    public void tick() {
        if (isPaused() || isCancelled()) {
            return;
        }
        
        T snapshot = createSnapshot();
        callback(snapshot);
        long oldTime = timeProperty.get();
        count();
        long newTime = timeProperty.get();
        
        if (oldTime != newTime) {
            unpause();
        } else {
            pause();
        }
    }
    
    /**
     * @return The cached amount to count by.
     */
    public final long getCountAmount() {
        return countAmount;
    }
    
    protected abstract void count();
    
    /**
     * Creates a ClockSnapshot for this Clock instance
     * @return The snapshot instance
     */
    public abstract T createSnapshot();
    
    protected abstract boolean shouldCallback(CallbackHolder<T> holder);
    
    /**
     * Contains logic for processing the {@link ClockCallback}'s. Please see the ClockCallback and the {@link CallbackHolder} classes for how this is processed
     */
    public void callback(T snapshot) {
        if (this.callbacks.isEmpty()) {
            return;
        }
        
        for (CallbackHolder<T> holder : this.callbacks.values()) {
            ClockCallback<T> callback = holder.getCallback();
            if (callback == null) {
                continue;
            }
            
            if (holder.getPeriod() <= 0) {
                continue;
            }
            
            if (!shouldCallback(holder)) {
                continue;
            }
            
            holder.setLastRun(timeProperty.get());
            callback.callback(snapshot);
        }
        
        if (endCondition != null) {
            if (endCondition.shouldEnd(snapshot)) {
                cancel();
            }
        } 
    }
    
    /**
     * This marks the start for the clock, please note: the clock will not start if no {@link ClockRunnable} is actually being ran.
     * @return The instance of the clock (Utility return value)
     */
    public Clock<T> start() {
        unpause();
        return this;
    }
    
    /**
     * Pauses the clock
     */
    public void pause() {
        this.paused = true;
    }
    
    /**
     * Resumes the clock
     */
    public void unpause() {
        this.paused = false;
    }
    
    /**
     * Marks for cancellation for the next run of the {@link ClockRunnable} <br>
     * Between calling this method and until it processes it again, it can be uncancelled
     */
    public void cancel() {
        this.cancelled = true;
    }
    
    /**
     * Removes the mark for cancellation. See the cancel() method
     */
    public void uncancel() {
        this.cancelled = false;
    }
    
    /**
     * Checks to see if it is marked for cancellation. If you call the uncancel() method before calling this one, you will get inaccurate results
     * @return If this clock is marked for cancellation
     */
    public boolean isCancelled() {
        return cancelled;
    }
    
    /**
     * The time is the value that is updated in the clock in milliseconds.
     * @return The current time for the clock
     */
    public long getTime() {
        return timeProperty.get();
    }
    
    /**
     * @return If this clock is currently paused.
     */
    public boolean isPaused() {
        return paused;
    }
    
    /**
     * Adds time to this clock. Please see the implementations for how this method affects them.
     * @param time The time to add
     */
    public void addTime(long time) {
        this.timeProperty.setValue(timeProperty.get() + time);
    }
    
    /**
     * Removes time from this clock. Please see the implementations for how this method affects them.
     * @param time The time to remove
     */
    public void removeTime(long time) {
        this.timeProperty.setValue(timeProperty.get() - time);
    }
    
    /**
     * Sets the time of this clock. Please see the implementations for how this method affects them.
     * @param time The time to set
     */
    public void setTime(long time) {
        this.timeProperty.setValue(time);
    }
    
    /**
     * Adds a non-repeating callback to this clock
     * @param callback The callback
     * @param runAtTime The time to run this callback at.
     * @return The UUID for the callback (Mostly internal) and for removing a callback
     */
    public UUID addCallback(ClockCallback<T> callback, long runAtTime) {
        return addCallback(callback, runAtTime, false);
    }
    
    public UUID addRepeatingCallback(ClockCallback<T> callback, long period) {
        return addCallback(callback, period, true);
    }

    public UUID addCallback(ClockCallback<T> callback, TimeUnit unit, long unitTime) {
        return addCallback(callback, unit.toMillis(unitTime), false);
    }

    public UUID addRepeatingCallback(ClockCallback<T> callback, TimeUnit unit, long unitTime) {
        return addCallback(callback, unit.toMillis(unitTime), true);
    }

    /**
     * Adds a callback to this clock
     * @param callback The callback instance
     * @param period The interval or period to run the callback at. This is relative to the time of the clock
     * @param repeating If the callback is a repeatable callback or if it is a one-time callback
     * @return The Unique ID for the callback. This is mostly an internal value
     */
    public UUID addCallback(ClockCallback<T> callback, long period, boolean repeating) {
        if (callback == null) {
            return null;
        }
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (this.callbacks.containsKey(uuid));

        this.callbacks.put(uuid, new CallbackHolder<>(callback, uuid, period, repeating));
        return uuid;
    }
    
    /**
     * Adds a callback to this clock
     * @param callback The callback
     * @return The UUID for the callback (Mostly internal) and for removing a callback
     */
    public UUID addCallback(ClockCallback<T> callback) {
        return addCallback(callback, callback.getPeriod());
    }
    
    /**
     * Removes the callback from this Clock 
     * @param uuid The uuid of the callback
     */
    public void removeCallback(UUID uuid) {
        this.callbacks.remove(uuid);
    }
    
    /**
     * See the {@link ClockEndCondition} documentation for how this works
     * @param endCondition The end condition
     */
    public void setEndCondition(ClockEndCondition<T> endCondition) {
        this.endCondition = endCondition;
    }
    
    /**
     * @return The current end condition. This can be null.
     */
    public ClockEndCondition<T> getEndCondition() {
        return endCondition;
    }
    
    /**
     * @param uuid The UUID of the callback
     * @return The callback instance
     */
    public ClockCallback<T> getCallback(UUID uuid) {
        CallbackHolder<T> holder = this.callbacks.get(uuid);
        if (holder != null) {
            return holder.getCallback();
        }
        
        return null;
    }
    
    public LongProperty timeProperty() {
        return this.timeProperty;
    }
}