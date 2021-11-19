package com.kwpugh.mob_catcher;

import com.kwpugh.mob_catcher.items.ItemMobCatcher;
import com.kwpugh.mob_catcher.items.ItemMobCatcherHostile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemInit
{
    static boolean enableHostile = MobCatcher.CONFIG.SETTINGS.enableHostileMobCatcherVersion;

    public static final Item MOB_CATCHER = new ItemMobCatcher((new Item.Settings()).maxCount(1).group(ItemGroup.MISC));
    public static final Item MOB_CATCHER_HOSTILE = new ItemMobCatcherHostile((new Item.Settings()).maxCount(1).group(ItemGroup.MISC));

    public static void init()
    {
        Registry.register(Registry.ITEM, new Identifier(MobCatcher.MOD_ID, "mob_catcher"), MOB_CATCHER);

        if(enableHostile)
        {
            Registry.register(Registry.ITEM, new Identifier(MobCatcher.MOD_ID, "mob_catcher_hostile"), MOB_CATCHER_HOSTILE);
        }
    }
}