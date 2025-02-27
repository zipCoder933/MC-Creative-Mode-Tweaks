package org.zipcoder.cmt.mixin.fastPlace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.zipcoder.cmt.client.fastPlace.SmartBlockPlacementClient;

import java.util.Objects;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    @Final
    Minecraft minecraft;

    @Unique
    public BlockHitResult lastBlockHit;
    @Unique
    public Vec3 lastHitPos;
    @Unique
    public BlockPos lastDirectionPos;
    @Unique
    public Direction lastDirection;
    @Unique
    public double maxDistance = 0;
    @Unique
    public Vec3 lastPlayerLookDirection = new Vec3(0, 0, 0);
    @Unique
    public Vec3 layerPlayerPosition = new Vec3(0, 0, 0);

    @Inject(method = "render", at = @At("TAIL"))
    public void tickUse(CallbackInfo ci) {
        if (!SmartBlockPlacementClient.getEnabledSmartPlacement()) return;  //If smart placement is enabled
        if (minecraft.player == null) return; //If the player is not null

        /**
         * Set blocks if the use key is down
         */
        if (minecraft.options.keyUse.isDown() && !minecraft.player.isUsingItem()) {
            if (checkValues()
//                 SmartBlockPlacementClient.tickPlacement == 0 &&
            ) {
//                SmartBlockPlacementClient.tickPlacement = SmartBlockPlacementClient.PLACEMENT_INTERVAL;
                ((MinecraftAccessor) minecraft).callStartUseItem();
                setValuesAfterBlockSet();
            }
        }
        /**
         * Set blocks if the attack key is down
         */
        else if (minecraft.options.keyAttack.isDown() && !minecraft.player.isUsingItem()) {
            if (checkValues()) {
                ((MinecraftAccessor) minecraft).callStartAttack();
                setValuesAfterBlockSet();
            }
        }
        /**
         * If we are not holding the use or attack key, we can reset everything
         */
        else {
            lastBlockHit = null;
            lastHitPos = null;
            lastDirectionPos = null;
            lastDirection = null;
            maxDistance = 0;
//            SmartBlockPlacementClient.tickPlacement = 0;
        }
    }

    /**
     * Return if we can set another block, given the previous state
     *
     * @return
     */
    @Unique
    public boolean checkValues() {
        assert minecraft.player != null;

        //If the player has not moved its orientation, dont set a new block

        if (minecraft.player.getLookAngle().distanceTo(lastPlayerLookDirection) < 0.02
                && minecraft.player.getEyePosition().distanceTo(layerPlayerPosition) < 0.02) return false;

        if (minecraft.hitResult instanceof BlockHitResult result) {
            if (blockHitEquals(lastBlockHit, result)) return false;
            if (lastDirectionPos != null && lastDirectionPos.equals(result.getBlockPos())) {
                if (lastDirection == null) {
                    lastDirection = result.getDirection();
                    return false;
                }
                if (lastDirection == result.getDirection()) {
                    return (lastHitPos.distanceToSqr(minecraft.player.getEyePosition()) > maxDistance);
                }
            } else {
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * Return if the 2 block hit results are equal
     *
     * @param a
     * @param b
     * @return
     */
    @Unique
    private static boolean blockHitEquals(BlockHitResult a, BlockHitResult b) {
        if (a == null || b == null) return false;
        return
                a.getBlockPos().equals(b.getBlockPos())
                        && a.getDirection() == b.getDirection();
    }

    @Unique
    public void setValuesAfterBlockSet() {
        assert minecraft.player != null;
        if (minecraft.hitResult instanceof BlockHitResult result) {
            lastBlockHit = result;
            lastHitPos = result.getBlockPos().getCenter();
            lastDirectionPos = result.getBlockPos().relative(result.getDirection());
            if (maxDistance == 0)
                maxDistance = Math.max(lastHitPos.distanceToSqr(minecraft.player.getEyePosition()) + 1.5, 1);
            lastDirection = null;
        }
        layerPlayerPosition = minecraft.player.getEyePosition();
        lastPlayerLookDirection = minecraft.player.getLookAngle();
    }
}
