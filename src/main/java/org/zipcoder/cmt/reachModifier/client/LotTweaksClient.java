package org.zipcoder.cmt.reachModifier.client;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.glfw.GLFW;
import org.zipcoder.cmt.reachModifier.LotTweaks;
import org.zipcoder.cmt.reachModifier.client.keys.AdjustRangeKey;
import org.zipcoder.cmt.reachModifier.client.keys.ReplaceKey;

@OnlyIn(Dist.CLIENT)
public class LotTweaksClient
{
	private static KeyMapping[] keyMappings = {
			new ReplaceKey(GLFW.GLFW_KEY_V, LotTweaks.NAME),
			new AdjustRangeKey(GLFW.GLFW_KEY_R, LotTweaks.NAME)
	};

	public LotTweaksClient() {
	}

	public static void init() {
		for (KeyMapping key : keyMappings) {
			MinecraftForge.EVENT_BUS.register(key);
		}
		MinecraftForge.EVENT_BUS.register(new LotTweaksClient());
	}

	public static void onRegisterKeyMappingsEvent(RegisterKeyMappingsEvent event) {
		for (KeyMapping key : keyMappings) {
			event.register(key);
		}
	}

	public static void onRegisterGuiOverlaysEvent(RegisterGuiOverlaysEvent event) {
		for (KeyMapping key : keyMappings) {
			if (key instanceof IGuiOverlay) {
				event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), key.getName(), (IGuiOverlay)key);
			}
		}
	}
}
