package com.kwpugh.mob_catcher.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

import static com.kwpugh.mob_catcher.items.ItemMobCatcher.getActionResult;

/*
    Version of catcher that only works on hostile  mobs
 */
public class ItemMobCatcherHostile extends Item {
    public ItemMobCatcherHostile(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        return getActionResult(context);
    }

    // Have glint if it contains a mob
    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt() && !stack.getOrCreateSubNbt("captured_entity").isEmpty();
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.mob_catcher.mob_catcher_hostile.tip1").formatted(Formatting.GREEN));

        if (stack.hasNbt() && !stack.getOrCreateSubNbt("captured_entity").isEmpty()) {
            tooltip.add((Text.translatable("item.mob_catcher.mob_catcher.tip3", Objects.requireNonNull(stack.getNbt())
                    .getString("name")).formatted(Formatting.YELLOW)));
        }
    }
}