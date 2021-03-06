package net.lomeli.gels.core;

public class Strings {
    public static final String MODID = "GelsPlus";
    public static final String NAME = "Gels+";
    public static final int MAJOR = 2, MINOR = 0, REVISION = 0;
    public static final String VERSION = MAJOR + "." + MINOR + "." + REVISION;
    public static final String XML = "https://dl.dropboxusercontent.com/u/17430088/Minecraft%20Mods/GelsPlus/updateXML.xml";

    public static final String CLIENT = "net.lomeli.gels.client.ClientProxy";
    public static final String COMMON = "net.lomeli.gels.core.Proxy";

    public static final String USER = "ohaiiChun";

    public static final String allowThrowableInfo = "Allow gel blobs to be throwable (if gel type is registered as throwable).";
    public static final String updateInfo = "Check for updates?";
    public static final String effectInfo = "Enable gel painting/effects when entities are hit by blobs";
    public static final String dispenserTick = "Cool down time for the gel dispenser.";
    public static final String enableCT = "Enable connected textures for the gels.";
    public static final String blackList = "Enter the class for the entity to wish to be immune to the gels.\nSeparate each class with semicolons\nExample: net.lomeli.gels.entity.EntityGelThrowable;thermalexpansion.entity.monster.EntityBlizz";
    public static final String blockList = "Enter the names for the block you wish to be black listed from having gel on.\nSeparate each name with a |.\nExample: minecraft:redstone_ore|IC2:blockOreUran|TConstruct:SearedBrick";
}
