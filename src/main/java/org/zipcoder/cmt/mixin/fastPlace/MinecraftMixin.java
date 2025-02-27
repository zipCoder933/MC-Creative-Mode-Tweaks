package org.zipcoder.cmt.mixin.fastPlace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.zipcoder.cmt.client.fastPlace.SmartBlockPlacementClient;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow
    public Options options;

    /**
     * Disables the auto placement if smart placement is enabled
     *
     * @param ci
     */
    @Inject(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;startUseItem()V"), cancellable = true)
    public void cancelUse(CallbackInfo ci) {
        if (SmartBlockPlacementClient.getEnabledSmartPlacement()) ci.cancel();
    }

    /**
     * Disables the auto attack if smart placement is enabled
     *
     * @param ci
     */
    @Inject(method = "handleKeybinds", at = @At("HEAD"), cancellable = true)
    public void cancelAttack(CallbackInfo ci) {
        if (SmartBlockPlacementClient.getEnabledSmartPlacement() && options.keyAttack.isDown()) ci.cancel();
    }
}
