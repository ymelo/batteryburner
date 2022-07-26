# batteryburner
Drains the battery of a device - A developer oriented tool

This tool is not intended for normal usage.
This tool will heavily drain the battery of a device until stopped, or until the device shuts down.

# When to use this tool
Almost never :).
If you can reproduce the effect in the emulator, do so.
In rare cases, where a problem seems to be related to a limited set of physical devices, this tool can be used to drain the battery for testing purposes.

# How to use this tool
There are currently two major features in the app.

## Screen on
In order to maintain the battery drain, the app needs to remain active.
The "Keep screen on" prevents the screen from turning off, which helps keeping the app active.

## CPU
This feature allows to spawn a number of threads lower or equal to the number of cores available.
Each thread will do synthetic calculation to keep the CPU busy.

## Flashlight
Turns on the flashlight (torchlight).


