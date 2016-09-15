CHIP 8 by Casey Cabrales
========================

A TDD emulator referenced from the [secondson repository](https://github.com/secondsun/chip8).

## Prerequisites

You need to have [Java 8](http://java.oracle.com) and [Maven](http://maven.apache.org) installed.

# Exec

The Chip8 can be run in a Swing window

```bash
mvn exec:java  -Dexec.mainClass="com.cabrales.console.chip8.SwingMain"
```

You can find some roms online or load the ones included in the src/main/resources/roms folder.