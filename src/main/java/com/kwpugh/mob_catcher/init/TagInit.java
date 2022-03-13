package com.kwpugh.mob_catcher.init;

import net.minecraft.entity.EntityType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TagInit
{
    public static final TagKey<EntityType<?>> MOBS_PASSIVE = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier("mob_catcher", "mobs_passive"));
    public static final TagKey<EntityType<?>> MOBS_HOSTILE = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier("mob_catcher", "mobs_hostile"));
}