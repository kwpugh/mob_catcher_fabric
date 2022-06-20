package com.kwpugh.mob_catcher.items;

import com.kwpugh.mob_catcher.MobCatcher;
import com.kwpugh.mob_catcher.init.TagInit;
import com.kwpugh.mob_catcher.util.CatcherUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.List;

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

    boolean useDatapack = MobCatcher.CONFIG.SETTINGS.enableDatapackMobTypes;

    // Right-click on entity, if right type, save entity info to tag and delete entity
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand)
    {
        if(!player.world.isClient)
        {
            if(useDatapack)
            {
                // Datapack tag-based checking
                EntityType<?> entityType = entity.getType();
                boolean inPassiveTag = entityType.isIn(TagInit.MOBS_PASSIVE);

                if(stack.getOrCreateNbt().isEmpty() && inPassiveTag)
                {
                    if(CatcherUtil.saveEntityToStack(entity, stack))
                    {
                        player.setStackInHand(hand, stack);
                    }

                    return ActionResult.SUCCESS;
                }
            }
            else
            {
                // Traditional hard-coded logic
                if((stack.getOrCreateNbt().isEmpty()) &&
                        (entity instanceof AnimalEntity ||
                                entity instanceof TameableEntity ||
                                entity instanceof GolemEntity ||
                                entity instanceof SquidEntity ||
                                entity instanceof FishEntity ||
                                entity instanceof VillagerEntity ||
                                entity instanceof AllayEntity ||
                        entity instanceof WanderingTraderEntity))
                {
                    if(CatcherUtil.saveEntityToStack(entity, stack))
                    {
                        player.setStackInHand(hand, stack);
                    }

                    return ActionResult.SUCCESS;
                }
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
        tooltip.add(Text.translatable("item.mob_catcher.mob_catcher.tip1").formatted(Formatting.GREEN));

        if (stack.hasNbt() && !stack.getOrCreateSubNbt("captured_entity").isEmpty())
        {
            tooltip.add((Text.translatable("item.mob_catcher.mob_catcher.tip3", stack.getNbt().getString("name")).formatted(Formatting.YELLOW)));
        }
    }
}