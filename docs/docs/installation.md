# Installing StarClock and StarLib
The first thing you need to determine is the version of StarClock that you are using.  
Go to the [releases](https://github.com/StarDevelopmentLLC/StarClock/releases/) page on the GitHub and look for the `latest`.  
The release description will have which version(s) of StarLib work with StarClock. If no version of StarLib is listed, the latest version of StarLib is assumed.  

## Choosing a Build Tool
The two primary build tools for Java are Maven and Gradle. Pick one and stick with it.

## JitPack Repository
Star Development LLC uses JitPack as the repository for development. You must have this repository in whichever build tool you wish to use.  
### Maven
```xml 
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### Gradle
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

## StarLib
StarLib is an essential library used within StarClock, however, it does not package it by default, this is to allow compatibility with other projects as to not have too many duplicate dependencies.  
Please ensure you are installing the correct version of StarLib for the version of StarClock that you are using, see above for how to find this information.  
**Replace `{VERSION}` with the version of StarLib you are using!**
### Maven
```xml
<dependency>
    <groupId>com.github.StarDevelopmentLLC</groupId>
    <artifactId>StarLib</artifactId>
    <version>{VERSION}</version>
</dependency>
```
### Gradle
```groovy
dependencies {
    implementation 'com.github.StarDevelopmentLLC:StarLib:{VERSION}'
}
```
## StarClock
### Maven
```xml
<dependency>
    <groupId>com.github.StarDevelopmentLLC</groupId>
    <artifactId>StarClock</artifactId>
    <version>{VERSION}</version>
</dependency>
```
### Gradle
```groovy
dependencies {
    implementation 'com.github.StarDevelopmentLLC:StarClock:{VERSION}'
}
```