package com.kwpugh.mob_catcher.init;

import com.kwpugh.mob_catcher.MobCatcher;
import com.kwpugh.mob_catcher.items.ItemMobCatcher;
import com.kwpugh.mob_catcher.items.ItemMobCatcherHostile;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit
{
    public static final Item MOB_CATCHER = new ItemMobCatcher((new Item.Settings()).maxCount(1));
    public static final Item MOB_CATCHER_HOSTILE = new ItemMobCatcherHostile((new Item.Settings()).maxCount(1));

    public static void register()
    {
        Registry.register(Registry.ITEM, new Identifier(MobCatcher.MOD_ID, "mob_catcher"), MOB_CATCHER);
        Registry.register(Registry.ITEM, new Identifier(MobCatcher.MOD_ID, "mob_catcher_hostile"), MOB_CATCHER_HOSTILE);
    }
}