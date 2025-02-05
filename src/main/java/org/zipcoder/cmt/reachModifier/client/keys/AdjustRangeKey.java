package org.zipcoder.cmt.reachModifier.client.keys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.zipcoder.cmt.Config;
import org.zipcoder.cmt.reachModifier.client.renderer.TextRenderer;
import org.zipcoder.cmt.reachModifier.network.LTPacketHandler;

@OnlyIn(Dist.CLIENT)
public class AdjustRangeKey extends LTKeyBase implements IGuiOverlay {

	public AdjustRangeKey(int keyCode, String category) {
		super("lottweaks-adjustrange", keyCode, category);
	}

	@Override
	public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth, int screenHeight) {
		if (this.pressTime == 0) {
			return;
		}
		if (!Minecraft.getInstance().player.isCreative()) {
			return;
		}
		// Update dist
		Minecraft mc = Minecraft.getInstance();
		HitResult rayTraceResult = mc.getCameraEntity().pick(255.0, mc.getFrameTime(), false);
		double dist;
		if (rayTraceResult == null || rayTraceResult.getType() == HitResult.Type.MISS) {
			dist = Config.MAX_RANGE.get();
		} else {
			dist = Math.min(Config.MAX_RANGE.get(), mc.player.getEyePosition(partialTicks).distanceTo(rayTraceResult.getLocation()));
		}
		LTPacketHandler.sendReachRangeMessage(dist);
		// Render
		int distInt = (int)dist;
		String distStr = String.valueOf(distInt);
		TextRenderer.showMessage(guiGraphics, Minecraft.getInstance().getWindow(), distStr);
	}

}
