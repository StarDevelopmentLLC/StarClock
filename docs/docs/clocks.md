# Clocks and how they work
The `Clock` class is the base class for all clocks within StarClock. It contains much of the base functionality of each one to make it much simpler to maintain.  
The `Timer` and `Stopwatch` are the two implementations of the Clock class, and really, you should extend from one of these two if you want to do such things.  
See the [callbacks](callbacks.md) page for how those work as they require their own explaination.  
Going forward, we will be referencing the [source](https://github.com/StarDevelopmentLLC/StarClock/blob/main/src/main/java/com/stardevllc/starclock/Clock.java) of the Clock class. You may want to take a look at it to see what I am talking about.  
## Ticking
Clocks use a "ticking" concept, this is whenever the runnable processes clocks, this is based on the `countAmount`. So if the `countAmount` is one. It will "tick" every millisecond.  
This allows customization of how precise you want to have your clocks to be. I do recommend choosing the lowest option feasable. The `countAmount` determine the shortest time that a callback can run.  

## Snapshots
Clock Snapshots are a way of preserving the clock state when it is going to process callbacks. These are thread-safe and changes to snapshots are not reflected in clocks. 

## Pausing and Unpausing
This is just a boolean flag to tell the ClockRunnable to skip this clock if it is true. Nothing else happens when paused and this is the default state when creating a clock.  

## Cancelling and uncancelling
When you cancel a clock, it will remove the clock from the ClockManager on the **next run**, meaning you have until the next "tick" to uncancel the clock.  

## Non-Ending
Clocks by default do not auto-cancel when they hit their upper or lower bounds. Timers when they hit zero and stopwatches when they hit their end time flag.  
You can change the time on a Timer and Stopwatch and it will behave just as if it didn't get changed. if you change a stopwatch's end time, it will continue to run the callbacks.  
If you change the Timer's length, it will accout for elapsed time and continue on.

## End Condition
This is a functional interface to determine when a clock should end automatically. Callbacks **SHOULD NOT** be used to cancel a clock.  
Without an end condition, the clock will remain until the program ends, or it is cancelled.
Returning `true` means that the clock will be cancelled. Returning `false` will mean the clock will continue to run.  
End Conditions are checked as the last thing, after all callbacks are processed. 

## The Properties
The `timeProperty` is something from StarLib and it allows detection of changes to a value without having to make sure there are methods for it. You can call the `timeProperty()` method and either add a bind or changelistener and it will function like a StarLib Property.  
In Timer, the length is a Property, and in Stopwatch, the endTime is a Property. these have methods that are named the same as the fields to allow the full functionality of a `Property` to be used. 