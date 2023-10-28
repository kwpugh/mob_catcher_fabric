package com.kwpugh.mob_catcher.util;

import com.kwpugh.mob_catcher.MobCatcher;
import com.kwpugh.mob_catcher.init.ItemInit;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MobCatcherGroup
{
    private static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MobCatcher.MOD_ID, "items"));

    public static void addGroup()
    {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP, FabricItemGroup.builder()
                .displayName(Text.literal("Mob Catcher"))
                .icon(ItemInit.MOB_CATCHER::getDefaultStack)
                .entries((context, entries) -> {
                    entries.add(ItemInit.MOB_CATCHER);
                    entries.add(ItemInit.MOB_CATCHER_HOSTILE);
                }).build()
        );
    }
}
