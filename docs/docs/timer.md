# Timer
This is the `count down` clock and is used for starting at a certain time and then ending when it hits zero.  

## Creation
There are two ways that you can use to create a Timer, using the `new` keyword and the ClockManager instance.

### ClockManager Factory Method
The clockManager factory method `createTimer(long)` allows you to easily create a new timer without having to worry about constructor parameters.  
The `long` parameter is the start time for the timer.

```java
Timer timer = clockManager.createTimer(TimeUnit.SECONDS.toMillis(10));
```
Note: I am using the `TimeUnit` from [StarLib](https://github.com/StarDevelopmentLLC/StarLib), which StarClock needs to have on the class-path

### Constructor Method
For this way of creation, you cannot do it all in one line, instead you have to create an instance of it, then pass it into the `addClock()` method in the ClockManager instance.  
You also have to provide the countAmount value to the constructor as well.

```java
Timer timer = new Timer(TimeUnit.SECONDS.toMillis(10), clockManager.getCountAmount());
clockManager.addClock(timer);
```

For what it is worth, it is best to use Factory method as it does all of this for you.

## Usage
In order to fully use a Timer, you must understand callbacks. For that, please see the page [Callbacks](callbacks.md) in order to go into depth about these.  
For this example, I will use a repeating callback to demonstrate.
```java
timer.addRepeatingCallback(new ClockCallback<TimerSnapshot>() {
    @Override
    public void callback(TimerSnapshot snapshot) {
        System.out.println("Remaining Time: " + TimeUnit.SECONDS.fromMillis(snapshot.getTime()) + "s");
    }
}, TimeUnit.SECONDS, 1);
timer.start();
```

The above code snippet is the full expanded, here is what it looks like if you use lamdas
```java
timer.addRepeatingCallback(snapshot -> System.out.println("Remaining Time: " + TimeUnit.SECONDS.fromMillis(snapshot.getTime()) + "s"), TimeUnit.SECONDS, 1);
timer.start();
```

If we run this code we get output every second and it will stop outputting at 10 seconds.
```text
Remaining Time: 10s
Remaining Time: 9s
Remaining Time: 8s
Remaining Time: 7s
Remaining Time: 6s
Remaining Time: 5s
Remaining Time: 4s
Remaining Time: 3s
Remaining Time: 2s
Remaining Time: 1s
Remaining Time: 0s
```
Please see [Clocks](clocks.md) to get a full explaination of the internal workings and what you can do with all clocks and on how to automatically end them.