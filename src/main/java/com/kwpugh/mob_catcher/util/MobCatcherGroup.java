package com.kwpugh.mob_catcher.util;

import com.kwpugh.mob_catcher.MobCatcher;
import com.kwpugh.mob_catcher.init.ItemInit;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class MobCatcherGroup
{
    public static void addGroup()
    {
        // force class run when we want
    }

    private static final ItemGroup MOB_CATCHER_GROUP = FabricItemGroup.builder(new Identifier(MobCatcher.MOD_ID, "mob_catcher_group"))
            .icon(() -> new ItemStack(ItemInit.MOB_CATCHER))
            .entries((enabledFeatures, entries, operatorEnabled) -> {
                entries.add(ItemInit.MOB_CATCHER);
                entries.add(ItemInit.MOB_CATCHER_HOSTILE);
            })
            .build();
}
