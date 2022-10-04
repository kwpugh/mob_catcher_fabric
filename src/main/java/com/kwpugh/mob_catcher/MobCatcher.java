package com.kwpugh.mob_catcher;

import com.kwpugh.mob_catcher.config.ModConfig;
import com.kwpugh.mob_catcher.events.EntityInteractEvent;
import com.kwpugh.mob_catcher.init.ItemInit;
import com.kwpugh.mob_catcher.init.TagInit;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;

public class MobCatcher implements ModInitializer
{
    public static final String MOD_ID = "mob_catcher";
    public static final ModConfig CONFIG = AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new)).getConfig();

    @Override
    public void onInitialize()
    {
        TagInit.register();
        ItemInit.register();
        UseEntityCallback.EVENT.register(EntityInteractEvent::onUseEntity);
    }
}