package org.zipcoder.cmt.mixin.fastPlace;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.zipcoder.cmt.client.fastPlace.SmartBlockPlacementClient;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;startUseItem()V"), cancellable = true)
    public void cancelUse(CallbackInfo ci) {
        if (!SmartBlockPlacementClient.getEnabledSmartPlacement()) return;
        ci.cancel();
    }
}
