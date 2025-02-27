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

    @Inject(method = "render", at = @At("TAIL"))
    public void tickUse(CallbackInfo ci) {
        if (!SmartBlockPlacementClient.getEnabledSmartPlacement()) return;
        if (minecraft.player == null) return;
        if (minecraft.options.keyUse.isDown() && !minecraft.player.isUsingItem()) {
            if (SmartBlockPlacementClient.tickPlacement == 0 && checkValues()) {
                SmartBlockPlacementClient.tickPlacement = 4;
                ((MinecraftAccessor) minecraft).callStartUseItem();
                setPostValues();

            }
        } else {
            lastBlockHit = null;
            lastHitPos = null;
            lastDirectionPos = null;
            lastDirection = null;
            maxDistance = 0;
            SmartBlockPlacementClient.tickPlacement = 0;
        }
    }


    @Unique
    public boolean checkValues() {
        if (minecraft.hitResult instanceof BlockHitResult result) {
            if (blockHitEquals(lastBlockHit, result)) return false;
            if (lastDirectionPos != null && lastDirectionPos.equals(result.getBlockPos())) {
                assert minecraft.player != null;
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

    @Unique
    private static boolean blockHitEquals(BlockHitResult a, BlockHitResult b) {
        if (a == null || b == null) return false;
        return a.getBlockPos().equals(b.getBlockPos()) && a.getDirection() == b.getDirection();
    }

    @Unique
    public void setPostValues() {
        assert minecraft.player != null;
        if (minecraft.hitResult instanceof BlockHitResult result) {
            lastBlockHit = result;
            lastHitPos = result.getBlockPos().getCenter();
            lastDirectionPos = result.getBlockPos().relative(result.getDirection());
            if (maxDistance == 0)
                maxDistance = Math.max(lastHitPos.distanceToSqr(minecraft.player.getEyePosition()) + 1.5, 1);
            lastDirection = null;
        }
    }
}
