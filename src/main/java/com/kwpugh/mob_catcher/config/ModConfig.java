package com.kwpugh.mob_catcher.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "mob_catcher")
public class ModConfig extends PartitioningSerializer.GlobalData
{
    public Settings SETTINGS = new Settings();

    @Config(name = "general")
    public static class Settings implements ConfigData
    {
        @Comment("***********************"
                +"\nSettings"
                +"\n***********************")
        public boolean enableHostileOnPassiveCatcher = false;
        public boolean enableHostileMobCatcherVersion = true;
    }
}