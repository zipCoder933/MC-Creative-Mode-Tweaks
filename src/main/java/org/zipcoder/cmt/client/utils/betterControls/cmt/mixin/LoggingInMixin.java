package org.zipcoder.cmt.client.utils.betterControls.cmt.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.zipcoder.cmt.client.utils.betterControls.cmt.Config;
import org.zipcoder.cmt.client.utils.betterControls.cmt.client.utils.NoClipHandler;

@Mixin(ClientPlayerNetworkEvent.LoggingIn.class)
public class LoggingInMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onConstruct(MultiPlayerGameMode controller, LocalPlayer player, Connection networkManager, CallbackInfo ci) {
        System.out.println("Injected into LoggingIn constructor!");
        System.out.println("Player is logging in! NoClip: " + Config.NOCLIP_ON_LOGIN.get());
        NoClipHandler.setNoClip(Config.NOCLIP_ON_LOGIN.get());
    }
}
