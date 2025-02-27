//package org.zipcoder.cmt.mixin;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import org.zipcoder.cmt.Config;
//
//@Mixin(net.minecraft.client.Minecraft.class)
//public class FastPlaceMixin {
//
//    @Shadow
//    private int rightClickDelay;
//
//    @Inject(method = "startUseItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/InteractionHand;values()[Lnet/minecraft/world/InteractionHand;"))
//    private void onRightClick(CallbackInfo info) {
//        if (Config.isInstantPlacing) this.rightClickDelay = 0;
//    }
//}
