# StarClock
This is a library for managing timers and stopwatches, but it can be adapted by either extending the Timer or Stopwatch classes or by extending the Clock class directly
[![](https://www.jitpack.io/v/StarDevelopmentLLC/StarClock.svg)](https://www.jitpack.io/#StarDevelopmentLLC/StarClock)
## To use this Library
You must add JitPack as a repo, below is for Gradle  
```groovy
repositories {
    maven {
        url = 'https://www.jitpack.io'
    }
}
```  
Then to use this library as a dependency  
```goovy
dependencies {
    implementation 'com.github.StarDevelopmentLLC:StarClock:1.0.0-alpha.1'
}
```  
Please note that this library requires StarLib.  
You must shade this library in order to properly use it, or have it already on the class-path. Gradle has the Shadow Plugin you can use for this task.  
If you are using Maven, the Maven-Shade plugin works just fine.
