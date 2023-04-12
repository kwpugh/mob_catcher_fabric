package com.kwpugh.mob_catcher.util;

import com.kwpugh.mob_catcher.MobCatcher;
import com.kwpugh.mob_catcher.init.TagInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;
import java.util.Optional;

public class CatcherUtil {
    static boolean useDataPack = MobCatcher.CONFIG.SETTINGS.enableDataPackMobTypes;

    public static ActionResult catchPassiveMob(PlayerEntity player, Entity entity, ItemStack stack, Hand hand) {
        if (!player.world.isClient) {
            if (useDataPack) {
                // Data pack tag-based checking
                EntityType<?> entityType = entity.getType();
                boolean inPassiveTag = entityType.isIn(TagInit.MOBS_PASSIVE);

                if (stack.getOrCreateSubNbt("captured_entity").isEmpty() && inPassiveTag) {
                    if (saveEntityToStack(entity, stack)) {
                        player.setStackInHand(hand, stack);
                    }

                    return ActionResult.SUCCESS;
                }
            } else {
                // Traditional hard-coded logic
                if (stack.getOrCreateSubNbt("captured_entity").isEmpty() && entity instanceof MobEntity && !(entity instanceof Monster)) {
                    if (saveEntityToStack(entity, stack)) {
                        player.setStackInHand(hand, stack);
                    }

                    return ActionResult.SUCCESS;
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    public static ActionResult catchHostileMob(PlayerEntity player, Entity entity, ItemStack stack, Hand hand) {
        if (useDataPack) {
            // Data pack tag-based checking
            EntityType<?> entityType = entity.getType();
            boolean inHostileTag = entityType.isIn(TagInit.MOBS_HOSTILE);

            if (stack.getOrCreateSubNbt("captured_entity").isEmpty() && inHostileTag) {
                if (saveEntityToStack(entity, stack)) {
                    player.setStackInHand(hand, stack);
                }

                return ActionResult.SUCCESS;
            }
        } else {
            // Traditional hard-coded logic for hostile only
            if (stack.getOrCreateSubNbt("captured_entity").isEmpty() && entity instanceof Monster && !(entity instanceof WitherEntity)
            ) {
                if (saveEntityToStack(entity, stack)) {
                    player.setStackInHand(hand, stack);
                }

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.SUCCESS;
    }

    // Method to save an entity to a tag and remove entity from world
    public static boolean saveEntityToStack(Entity entity, ItemStack stack) {
        NbtCompound entityTag = new NbtCompound();

        if (!entity.saveSelfNbt(entityTag)) {
            return false;
        }

        stack.getOrCreateNbt().put("captured_entity", entityTag);
        stack.getOrCreateNbt().putString("name", entity.getDisplayName().getString());
        entity.discard();

        return true;
    }

    public static void respawnEntity(ItemUsageContext context, ItemStack stack) {
        ServerWorld serverWorld = (ServerWorld) context.getWorld();
        BlockPos pos = context.getBlockPos().offset(context.getSide());
        ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();

        NbtCompound entityTag = context.getStack().getSubNbt("captured_entity");   // KEEP
        Optional<Entity> entity = EntityType.getEntityFromNbt(Objects.requireNonNull(entityTag), serverWorld);

        if (entity.isPresent()) {
            Entity entity2 = entity.get();
            entity2.updatePositionAndAngles(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, Objects.requireNonNull(player).getYaw(), player.getPitch());
            serverWorld.spawnEntity(entity2);
        }

        stack.removeSubNbt("name");  //KEEP
        stack.removeSubNbt("captured_entity");  // KEEP

        Objects.requireNonNull(context.getPlayer()).getStackInHand(context.getHand());
    }
}
