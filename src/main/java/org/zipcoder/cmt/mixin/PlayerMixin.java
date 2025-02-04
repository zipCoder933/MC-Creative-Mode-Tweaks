package org.zipcoder.cmt.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.zipcoder.cmt.utils.mixin.Player_I;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements Player_I {


    /**
     * There is a server side and a client side version of each player
     * If we JUST set the variable on the server, it will fight with the existing variable on the client
     */
    //Unique is used to prevent from conflicting with other mixin varaibles
    @Unique
    private boolean noClipEnabled = false;



    @Inject(method = "<init>", at = @At("RETURN"))
    private void onPlayerInit(CallbackInfo ci) {
        if (Minecraft.getInstance().level != null) {
            // Running on the client
            System.out.println("Initializing client-side player with noClipEnabled = " + this.noClipEnabled);
        } else {
            // Running on the server
            System.out.println("Initializing server-side player with noClipEnabled = " + this.noClipEnabled);
        }
    }

    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract boolean isSpectator();

    @Shadow
    public abstract Abilities getAbilities();

    @Override
    public boolean isNoClip() {
        return noClipEnabled;
    }

    @Override
    public void setNoClip(boolean noClip) {
        this.noClipEnabled = noClip;
    }

    @Inject(
            method = "updatePlayerPose",
            at = @At(value = "TAIL")
    )
    private void modifyPose(CallbackInfo ci) {
        if(isNoClip()) this.setPose(Pose.STANDING);
    }

    /**
     * Inject right after noPhysics is set, but before any further logic.
     */
    @Inject(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/player/Player;noPhysics:Z",
                    opcode = Opcodes.PUTFIELD,
                    shift = At.Shift.AFTER,
                    ordinal = 0
            )
    )
    private void onTick_AfterNoPhysics(CallbackInfo ci) {
        // Modify noPhysics only if the player is a spectator and noClip is enabled.
        if (this.getAbilities().flying && isNoClip()) {
            this.noPhysics = true;
            this.setOnGround(false);
        }
    }



//    @Inject(method = "tick", at = @At("HEAD"))
//    private void onTick_Head(CallbackInfo ci) {
//        System.out.println("HEAD--------- NoClip: " + noClipEnabled);
//    }
//
//    @Inject(method = "tick", at = @At("TAIL"))
//    private void onTick_TAIL(CallbackInfo ci) {
//        System.out.println("TAIL--------- NoClip: " + noClipEnabled);
//    }

}
