package org.zipcoder.cmt.client.fastPlace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zipcoder.cmt.Config;
import org.zipcoder.cmt.client.rendering.TextRenderer;

import static org.zipcoder.cmt.CreativeModeTweaks.MODID;
import static org.zipcoder.cmt.client.ClientModEvents.KEY_TOGGLE_INSTAPLACE;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SmartBlockPlacementClient {

    public static void setEnabledSmartPlacement(boolean enable) {
        Config.fastPlacementEnabled = enable;
    }

    public static boolean getEnabledSmartPlacement() {
        return Config.fastPlacementEnabled;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Minecraft.getInstance().player != null) {
            LocalPlayer player = Minecraft.getInstance().player;

            if (Minecraft.getInstance().player.isCreative()) {
                if (KEY_TOGGLE_INSTAPLACE.consumeClick()) {
                    setEnabledSmartPlacement(!getEnabledSmartPlacement());
                    player.displayClientMessage(
                            Component.translatable("text.cmt.enabled_smart_placement",
                                    Component.translatable("text.cmt." + getEnabledSmartPlacement())), true);
                }
            } else if (getEnabledSmartPlacement()) {
                setEnabledSmartPlacement(false);
            }
        }
    }


//    private static int ticker = 20;
//    @SubscribeEvent
//    public static void onClientTick(TickEvent.ClientTickEvent event) {
//        if (ticker > 0) {
//            ticker--;
//        }
//        if (KEY_TOGGLE_INSTAPLACE.isDown() && Minecraft.getInstance().player != null && ticker <= 0) {
//            ticker = 20;
//            KEY_TOGGLE_INSTAPLACE.consumeClick();
//
//            if (Config.isInstantPlacing) {
//                Minecraft.getInstance().player.displayClientMessage(Component.translatable("message." + MODID + ".disabledinstaplace"), true);
//                //Minecraft.getInstance().player.playSound(SoundEvents.NOTE_BLOCK_PLING.get());
//                Config.isInstantPlacing = false;
//            } else {
//                Minecraft.getInstance().player.displayClientMessage(Component.translatable("message." + MODID + ".enabledinstaplace"), true);
//                //Minecraft.getInstance().player.playSound(SoundEvents.NOTE_BLOCK_CHIME.get());
//                Config.isInstantPlacing = true;
//            }
//
//        }
//    }
}
