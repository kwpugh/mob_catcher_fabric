package com.kwpugh.mob_catcher.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class CatcherUtil
{
    public static void respawnEntity(ItemUsageContext context, ItemStack stack)
    {
        ServerWorld serverWorld = (ServerWorld) context.getWorld();
        BlockPos pos = context.getBlockPos().offset(context.getSide());
        ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();

        NbtCompound entityTag = context.getStack().getSubNbt("captured_entity");   // KEEP

        Optional<Entity> entity = EntityType.getEntityFromNbt(entityTag, serverWorld);

        if(entity.isPresent())
        {
            Entity entity2 = entity.get();
            entity2.updatePositionAndAngles(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, player.getYaw(), player.getPitch());
            serverWorld.spawnEntity(entity2);
        }

        stack.removeSubNbt("name");  //KEEP
        stack.removeSubNbt("captured_entity");  // KEEP

        context.getPlayer().getStackInHand(context.getHand());
    }

    // Method to save an entity to a tag and remove entity from world
    public static boolean saveEntityToStack(Entity entity, ItemStack stack)
    {
        NbtCompound entityTag = new NbtCompound();

        if(!entity.saveSelfNbt(entityTag))
        {
            return false;
        }

        stack.getOrCreateNbt().put("captured_entity", entityTag);
        stack.getOrCreateNbt().putString("name", entity.getDisplayName().getString());
        entity.discard();

        return true;
    }
}
