Gels+
========

Just playing around making the gels from Portal 2 for fun.

----------

# How to compile #

1. Download and install JDK and Gradle
 * There is an excellent tutorial on how to do this on Equivalent Exchange 3 github page. see [here for java](https://github.com/pahimar/equivalent-exchange-3#setup-java) and [here for gradle](https://github.com/pahimar/equivalent-exchange-3#setup-gradle).
2. Download the source. It is recommended you use `git clone https://github.com/Lomeli12/GelsPlus` to download.
3. Go into the folder where you have downloaded the source.
4. Run `gradle clean setupCIWorkspace`
5. Run `gradle build`. *Will require an internet connection to download libraries.*
6. Compiled jar should be in *build/libs*.

# Using the API #

1. Setup your environment like normal.
2. Include `dependencies = "after:GelsPlus"` in your @Mod annotation.
3. Make a class for each gel that you want to make that extends GelAbility, making sure to fill all the methods that apply.
4. To register your gel, use the following function in your FMLInitializationEvent or FMLPostInitializationEvent.
    `GelAbility.gelRegistry.addGel(new YourCustomGel());`

## Note ##
This mod is not related to Portal Gun by [iChun](https://github.com/iChun/) and does not require it to run. However, it was made with Portal Gun in mind.