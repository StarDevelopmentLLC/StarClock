# Callbacks
When dealing with callbacks, the only other class you have to worry about is the [ClockCallback](https://github.com/StarDevelopmentLLC/StarClock/blob/main/src/main/java/com/stardevllc/starclock/callback/ClockCallback.java)  
The `CallbackHolder` class is used internally and not needed to be covered.  
The remainder of this file will use a `Timer` that lasts for 10 seconds. Please see [Timer](timer.md)s for this setup.  

## Repeating callbacks
You have already seen an example of a repeated callback in the basic usage class, I put that there because that is the type that will be used the most.

## One-Time Callbacks
These are callbacks that are called once, relative to the elapsed time of the clock.  
So in my example, lets say I want to have it also print a message at 5 seconds, look below for an example  
```java
timer.addCallback(snapshot -> System.out.println("5 seconds remain!"), TimeUnit.SECONDS, 5);
```
Output: 
```text
Remaining Time: 10s
Remaining Time: 9s
Remaining Time: 8s
Remaining Time: 7s
Remaining Time: 6s
Remaining Time: 5s
5 seconds remain!
Remaining Time: 4s
Remaining Time: 3s
Remaining Time: 2s
Remaining Time: 1s
Remaining Time: 0s
```
If you notice, the new message happened at the same time as the 5 second count and didn't happen again. The order isn't guarunteed due to the fact it uses a HashMap behind the scenes and not a linked one.  

If I bump up the total clock time to 15 seconds, it still works
```text
Remaining Time: 15s
Remaining Time: 14s
Remaining Time: 13s
Remaining Time: 12s
Remaining Time: 11s
Remaining Time: 10s
Remaining Time: 9s
Remaining Time: 8s
Remaining Time: 7s
Remaining Time: 6s
Remaining Time: 5s
5 seconds remain!
Remaining Time: 4s
Remaining Time: 3s
Remaining Time: 2s
Remaining Time: 1s
Remaining Time: 0s
```