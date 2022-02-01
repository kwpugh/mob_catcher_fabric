package com.kwpugh.mob_catcher.init;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class TagInit
{
    public static final Tag.Identified<EntityType<?>> MOBS_PASSIVE = TagFactory.ENTITY_TYPE.create(new Identifier("mob_catcher", "mobs_passive"));
    public static final Tag.Identified<EntityType<?>> MOBS_HOSTILE = TagFactory.ENTITY_TYPE.create(new Identifier("mob_catcher", "mobs_hostile"));
}