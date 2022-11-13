package com.kwpugh.mob_catcher.init;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKeys;

public class TagInit
{
    public static final TagKey<EntityType<?>> MOBS_PASSIVE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier("mob_catcher", "mobs_passive"));
    public static final TagKey<EntityType<?>> MOBS_HOSTILE = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier("mob_catcher", "mobs_hostile"));

    public static void register()
    {
        // no op
    }
}