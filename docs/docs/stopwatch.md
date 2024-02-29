~~# Stopwatch
This type of clock is a `count up` type of clock, meaning it will start from zero and go until the end time, or until it stops.  
You **MUST** provide an end time. If you don't bad things will happen. This is a bug as of now, and a future update will fix this issue.  

## Creation
There are two ways that you can use to create a Stopwatch, using the `new` keyword and the ClockManager instance. 

### ClockManager Factory Method
The clockManager factory method `createStopwatch(long)` allows you to easily create a new stopwatch without having to worry about constructor parameters.  
The `long` parameter is the end time for the stopwatch. 

```java
Stopwatch stopwatch = clockManager.createStopwatch(TimeUnit.SECONDS.toMillis(10));
```
Note: I am using the `TimeUnit` from [StarLib](https://github.com/StarDevelopmentLLC/StarLib), which StarClock needs to have on the class-path

### Constructor Method
For this way of creation, you cannot do it all in one line, instead you have to create an instance of it, then pass it into the `addClock()` method in the ClockManager instance.  
You also have to provide the countAmount value to the constructor as well.  

```java
Stopwatch stopwatch = new Stopwatch(TimeUnit.SECONDS.toMillis(10), clockManager.getCountAmount());
clockManager.addClock(stopwatch);
```

For what it is worth, it is best to use Factory method as it does all of this for you.  

## Usage
In order to fully use a Stopwatch, you must understand callbacks. For that, please see the page [Callbacks](callbacks.md) in order to go into depth about these.  
For this example, I will use a repeating callback to demonstrate.  
```java
stopwatch.addRepeatingCallback(new ClockCallback<StopwatchSnapshot>() {
    @Override
    public void callback(StopwatchSnapshot snapshot) {
        System.out.println("Clock Time: " + TimeUnit.SECONDS.fromMillis(snapshot.getTime()) + "s");
    }
}, TimeUnit.SECONDS, 1);
stopwatch.start();
```

The above code snippet is the full expanded, here is what it looks like if you use lamdas
```java
stopwatch.addRepeatingCallback(snapshot -> System.out.println("Clock Time: " + TimeUnit.SECONDS.fromMillis(snapshot.getTime()) + "s"), TimeUnit.SECONDS, 1);
stopwatch.start();
```

If we run this code we get output every second and it will stop outputting at 10 seconds.  
```text
Clock Time: 0s
Clock Time: 1s
Clock Time: 2s
Clock Time: 3s
Clock Time: 4s
Clock Time: 5s
Clock Time: 6s
Clock Time: 7s
Clock Time: 8s
Clock Time: 9s
Clock Time: 10s
```
Please see [Clocks](clocks.md) to get a full explaination of the internal workings and what you can do with all clocks and on how to automatically end them.