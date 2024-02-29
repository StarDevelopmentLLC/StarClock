# Getting Started
This page will show you the prerequisits that you need to have in order to use this library

## Timing System
You must have some way of having repeated calls to a method using a `Runnable`.  
There are multiple ways that you can do this, however this is out of the scope of this Wiki. You can use the `java.util.Timer` or use the Spigot `Scheduler`.  
For the rest of this example, I will be using the Java Timer method so that I can have millisecond precision. 

## Installation
Please see [Installation](installation.md) to see how to install StarClock and StarLib

## ClockManager
This class is the entry-point into the library. You must obtain an instance to this class in some way.  
The easiest way is to just create an instance of it, however, if you are using `StarCore`, you can obtain it using the `ServiceManager`

### `logger` Parameter
This parameter is to allow printing of some information to the logger. This can be `null`. 

### `countAmount` Parameter
This parameter is how much time in `milliseconds` that the clocks registered to this manager count by. It is recommended to use the lowest denomination that you wish to use.  
For this example, I will be using 1 millisecond. For Spigot/Paper, the **lowest** that you can do is `50` milliseconds. 

### Example Creation
```java
ClockManager clockManager = new ClockManager(null, 1);
```