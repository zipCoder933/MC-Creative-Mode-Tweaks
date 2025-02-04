package org.zipcoder.cmt.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Shadow
    public abstract boolean isSpectator();

    @Shadow
    public abstract Abilities getAbilities();

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
        if (this.getAbilities().flying) {
            this.noPhysics = true;
            this.setOnGround(false);
        }
        //System.out.println("Modified code--------- Spectator: " + this.isSpectator() + " noPhysics: " + this.noPhysics);
    }

}
