package com.kwpugh.mob_catcher;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/*
 * This item also relies on mixins
 * into WolfEntity, ParrotEntity, VillagerEntity, AbstractDonkeyEntity, and
 * WanderingTraderEntity to change interactMob
 * methods and bypass usually GUIs or actions
 * Sync with Gobber Staff of Ensnarement if changes occur
 */
public class ItemMobCatcher extends Item
{
    public ItemMobCatcher(Settings settings)
    {
        super(settings);
    }

    static boolean enableHostileUse = MobCatcher.CONFIG.SETTINGS.enableHostileOnPassiveCatcher;

    // Right-click on entity, if right type, save entity info to tag and delete entity
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand)
    {
        if(!player.world.isClient)
        {
            if((enableHostileUse) && (stack.getOrCreateNbt().isEmpty()) &&
                    (entity instanceof HostileEntity) && !(entity instanceof WitherEntity))
            {
                if(CatcherUtil.saveEntityToStack(entity, stack))
                {
                    player.setStackInHand(hand, stack);
                }

                return ActionResult.SUCCESS;
            }

            if((stack.getOrCreateNbt().isEmpty()) &&
                    (entity instanceof AnimalEntity ||
                            entity instanceof GolemEntity ||
                            entity instanceof SquidEntity ||
                            entity instanceof FishEntity ||
                            entity instanceof VillagerEntity) ||
                    entity instanceof WanderingTraderEntity)
            {
                if(CatcherUtil.saveEntityToStack(entity, stack))
                {
                    player.setStackInHand(hand, stack);
                }

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.SUCCESS;
    }

    // Right-click on block, if staff has stored entity set it's position, spawn it in, and remove tags on staff
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
        tooltip.add(new TranslatableText("item.mob_catcher.mob_catcher.tip1").formatted(Formatting.GREEN));

        if (stack.hasNbt() && !stack.getOrCreateSubNbt("captured_entity").isEmpty())
        {
            tooltip.add((new TranslatableText("item.mob_catcher.mob_catcher.tip3", stack.getNbt().getString("name")).formatted(Formatting.YELLOW)));
        }
    }
}