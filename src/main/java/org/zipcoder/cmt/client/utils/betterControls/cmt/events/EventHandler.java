package org.zipcoder.cmt.client.utils.betterControls.cmt.events;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.zipcoder.cmt.client.utils.betterControls.cmt.Config;
import org.zipcoder.cmt.client.utils.betterControls.cmt.utils.MathUtils;
import org.zipcoder.cmt.client.utils.betterControls.cmt.utils.reach.AdjustRangeHelper;

import static org.zipcoder.cmt.CreativeModeTweaks.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();
//        player.sendSystemMessage(Component.literal("Welcome, " + player.getName().getString() + "!"));

        /**
         * We want to set the default reach distance if the player is in creative
         */
        if (player.gameMode.getGameModeForPlayer() == GameType.CREATIVE) {
            double dist = MathUtils.clamp(Config.REACH_DEFAULT_RANGE.get(), Config.REACH_MIN_RANGE.get(), Config.REACH_MAX_RANGE.get());
            AdjustRangeHelper.changeRangeModifier(player, dist);
        }
    }

    @SubscribeEvent
    public static void onGameModeChange(PlayerEvent.PlayerChangeGameModeEvent event) {

        /**
         * We want to set the default reach distance if the player is in creative
         */
        if (event.getNewGameMode() == GameType.CREATIVE) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            double dist = MathUtils.clamp(Config.REACH_DEFAULT_RANGE.get(), Config.REACH_MIN_RANGE.get(), Config.REACH_MAX_RANGE.get());
            AdjustRangeHelper.changeRangeModifier(player, dist);
        }
    }
}
