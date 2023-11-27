package com.stardevllc.starclock.condition;

import com.stardevllc.starclock.Clock;
import com.stardevllc.starclock.snapshot.ClockSnapshot;

/**
 * The return of this class determines if a clock should end. This condition is as if you called the {@link Clock#cancel()} method. 
 * @param <T> The Snapshot type
 */
@FunctionalInterface
public interface ClockEndCondition<T extends ClockSnapshot> {
    /**
     * @param snapshot The Snapshot
     * @return true if the clock should be cancelled and not run. false to allow the clock to continue
     */
    boolean shouldEnd(T snapshot);
}
