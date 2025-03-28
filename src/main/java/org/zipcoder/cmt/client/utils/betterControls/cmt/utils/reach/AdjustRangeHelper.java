package org.zipcoder.cmt.client.utils.betterControls.cmt.utils.reach;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

import static org.zipcoder.cmt.CreativeModeTweaks.MODID;

public class AdjustRangeHelper {

	private static final UUID _UUID = new UUID(2457550121339451521L, 1595282694073824061L);
	private static final String NAME = MODID + "reach";

	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent event) {
		removeOldRangeModifiers(event.getEntity());
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.side.isServer() && !event.player.isCreative()) {
			clearRangeModifier(event.player);
		}
	}

	//Just remove all of them!
	public static void removeOldRangeModifiers(Player player) {
		AttributeInstance instance = player.getAttribute(ForgeMod.BLOCK_REACH.get());
		for (AttributeModifier modifier: instance.getModifiers()) {
			if (modifier.getName().equals(MODID)) {
				instance.removeModifier(modifier);
			}
		}
	}
	
	private static void clearRangeModifier(Player player) {
		player.getAttribute(ForgeMod.BLOCK_REACH.get()).removeModifier(_UUID);
	}

	public static void changeRangeModifier(Player player, double dist) {
		clearRangeModifier(player);
		AttributeInstance instance = player.getAttribute(ForgeMod.BLOCK_REACH.get());
		instance.addPermanentModifier(new AttributeModifier(_UUID, NAME, dist - instance.getBaseValue() + 0.5, AttributeModifier.Operation.ADDITION));
	}
	
}
