package com.kwpugh.mob_catcher.items;

import com.kwpugh.mob_catcher.util.CatcherUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ItemMobCatcher extends Item {
    public ItemMobCatcher(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        return getActionResult(context);
    }

    @NotNull
    static ActionResult getActionResult(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        if (!(context.getWorld() instanceof ServerWorld)) return ActionResult.SUCCESS;
        if (!context.getWorld().isClient && stack.hasNbt() && Objects.requireNonNull(stack.getNbt()).contains("captured_entity")) {
            CatcherUtil.respawnEntity(context, stack);

            return ActionResult.SUCCESS;
        }

        return ActionResult.SUCCESS;
    }

    // Have glint if it contains a mob
    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt() && !stack.getOrCreateSubNbt("captured_entity").isEmpty();
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.mob_catcher.mob_catcher.tip1").formatted(Formatting.GREEN));

        if (stack.hasNbt() && !stack.getOrCreateSubNbt("captured_entity").isEmpty()) {
            tooltip.add((Text.translatable("item.mob_catcher.mob_catcher.tip3", Objects.requireNonNull(stack.getNbt())
                    .getString("name")).formatted(Formatting.YELLOW)));
        }
    }
}