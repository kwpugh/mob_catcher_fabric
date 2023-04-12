package com.kwpugh.mob_catcher.init;

import com.kwpugh.mob_catcher.MobCatcher;
import com.kwpugh.mob_catcher.items.ItemMobCatcher;
import com.kwpugh.mob_catcher.items.ItemMobCatcherHostile;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemInit {
    public static final Item MOB_CATCHER = new ItemMobCatcher(new FabricItemSettings().maxCount(1));
    public static final Item MOB_CATCHER_HOSTILE = new ItemMobCatcherHostile(new FabricItemSettings().maxCount(1));

    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(MobCatcher.MOD_ID, "mob_catcher"), MOB_CATCHER);
        Registry.register(Registries.ITEM, new Identifier(MobCatcher.MOD_ID, "mob_catcher_hostile"), MOB_CATCHER_HOSTILE);
    }
}