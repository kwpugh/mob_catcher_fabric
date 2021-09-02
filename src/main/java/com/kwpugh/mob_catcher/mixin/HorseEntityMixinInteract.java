package com.kwpugh.mob_catcher.mixin;

import com.kwpugh.mob_catcher.ItemInit;

import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(HorseEntity.class)
public class HorseEntityMixinInteract
{
    @Inject(method = "interactMob", at = @At(value = "HEAD"), cancellable = true)
    public void mobCatcherInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir)
    {
        ItemStack stack = player.getStackInHand(hand);

        World world = player.getEntityWorld();

        if(!world.isClient)
        {
            if(stack.getItem() == ItemInit.MOB_CATCHER)
            {
                player.swingHand(hand);

                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}

