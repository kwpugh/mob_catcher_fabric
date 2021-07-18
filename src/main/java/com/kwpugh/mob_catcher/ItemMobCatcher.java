package com.kwpugh.mob_catcher;

import java.util.List;
import java.util.Optional;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
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
            if((enableHostileUse) && (stack.getOrCreateTag().isEmpty()) &&
                    (entity instanceof HostileEntity) && !(entity instanceof WitherEntity))
            {
                if(saveEntityToStack(entity, stack, player))
                {
                    player.setStackInHand(hand, stack);
                }

                return ActionResult.SUCCESS;
            }

            if((stack.getOrCreateTag().isEmpty()) &&
                    (entity instanceof AnimalEntity ||
                            entity instanceof GolemEntity ||
                            entity instanceof VillagerEntity) ||
                    entity instanceof WanderingTraderEntity)
            {
                if(saveEntityToStack(entity, stack, player))
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
        if(!context.getWorld().isClient && stack.hasTag() && stack.getTag().contains("captured_entity"))
        {
            ServerWorld serverWorld = (ServerWorld) context.getWorld();
            BlockPos pos = context.getBlockPos().offset(context.getSide());
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();

            NbtCompound entityTag = context.getStack().getSubTag("captured_entity");   // KEEP

            Optional<Entity> entity = EntityType.getEntityFromNbt(entityTag, serverWorld);

            if(entity.isPresent())
            {
                Entity entity2 = entity.get();
                entity2.updatePositionAndAngles(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, player.getYaw(), player.getPitch());
                serverWorld.spawnEntity(entity2);
            }

            stack.removeSubTag("name");  //KEEP
            stack.removeSubTag("captured_entity");  // KEEP

            context.getPlayer().getStackInHand(context.getHand());

            return ActionResult.SUCCESS;
        }

        return ActionResult.SUCCESS;
    }

    // Method to save an entity to a tag and remove entity from world
    public boolean saveEntityToStack(Entity entity, ItemStack stack, PlayerEntity player)
    {
        NbtCompound entityTag = new NbtCompound();

        if(!entity.saveSelfNbt(entityTag))
        {
            return false;
        }

        stack.getOrCreateTag().put("captured_entity", entityTag);
        stack.getOrCreateTag().putString("name", entity.getDisplayName().getString());
        entity.discard();

        return true;
    }

    // Have glint if it contains a mob
    @Override
    public boolean hasGlint(ItemStack stack)
    {
        return stack.hasTag() && !stack.getOrCreateSubTag("captured_entity").isEmpty();
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext tooltipContext)
    {
        tooltip.add(new TranslatableText("item.mob_catcher.mob_catcher.tip1").formatted(Formatting.GREEN));

        if (stack.hasTag() && !stack.getOrCreateSubTag("captured_entity").isEmpty())
        {
            tooltip.add((new TranslatableText("item.mob_catcher.mob_catcher.tip3", stack.getTag().getString("name")).formatted(Formatting.YELLOW)));
        }
    }
}