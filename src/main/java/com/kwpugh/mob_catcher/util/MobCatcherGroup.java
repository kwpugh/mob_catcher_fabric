package com.kwpugh.mob_catcher.util;

import com.kwpugh.mob_catcher.MobCatcher;
import com.kwpugh.mob_catcher.init.ItemInit;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;

public class MobCatcherGroup
{
    public static void addGroup()
    {
        // force class run when we want
    }

    public static final ItemGroup MOB_CATCHER_GROUP = new FabricItemGroup(new Identifier(MobCatcher.MOD_ID, "mob_catcher_group"))
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ItemInit.MOB_CATCHER);
        }

        @Override
        protected void addItems(FeatureSet enabledFeatures, Entries entries)
        {
            entries.add(ItemInit.MOB_CATCHER);
            entries.add(ItemInit.MOB_CATCHER_HOSTILE);
        }
    };
}
