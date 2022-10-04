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

import java.util.List;

/*
    Version of catcher that only works on hostile  mobs
 */
public class ItemMobCatcherHostile extends Item
{
    public ItemMobCatcherHostile(Settings settings)
    {
        super(settings);
    }

    @SuppressWarnings("resource")
    public ActionResult useOnBlock(ItemUsageContext context)
    {
        ItemStack stack = context.getStack();
        if(!(context.getWorld() instanceof ServerWorld)) return ActionResult.SUCCESS;
        if(!context.getWorld().isClient && stack.hasNbt() && stack.getNbt().contains("captured_entity"))
        {
            CatcherUtil.respawnEntity(context, stack);

            return ActionResult.SUCCESS;
        }

        return ActionResult.SUCCESS;
    }

    // Have glint if it contains a mob
    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return stack.hasNbt() && !stack.getOrCreateSubNbt("captured_entity").isEmpty();
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext tooltipContext)
    {
        tooltip.add(Text.translatable("item.mob_catcher.mob_catcher_hostile.tip1").formatted(Formatting.GREEN));

        if (stack.hasNbt() && !stack.getOrCreateSubNbt("captured_entity").isEmpty())
        {
            tooltip.add((Text.translatable("item.mob_catcher.mob_catcher.tip3", stack.getNbt().getString("name")).formatted(Formatting.YELLOW)));
        }
    }
}