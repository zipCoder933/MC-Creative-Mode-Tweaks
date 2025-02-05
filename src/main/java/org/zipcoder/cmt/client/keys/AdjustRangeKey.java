package org.zipcoder.cmt.client.keys;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.zipcoder.cmt.Config;
import org.zipcoder.cmt.client.rendering.TextRenderer;
import org.zipcoder.cmt.network.packets.PacketHandler;
import org.zipcoder.cmt.utils.MathUtils;

@OnlyIn(Dist.CLIENT)
public class AdjustRangeKey extends KeyBase implements IGuiOverlay {

	public AdjustRangeKey(String name, int keyCode, String category) {
		super(name, keyCode, category);
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
			dist = Config.REACH_MAX_RANGE.get();
		} else {
			dist = mc.player.getEyePosition(partialTicks).distanceTo(rayTraceResult.getLocation());
			dist = MathUtils.clamp(dist, Config.REACH_MIN_RANGE.get(), Config.REACH_MAX_RANGE.get());
		}
		PacketHandler.sendReachRangeMessage(dist);
		// Render
		int distInt = (int)dist;
		String distStr = String.valueOf(distInt);
		TextRenderer.showMessage(guiGraphics, Minecraft.getInstance().getWindow(), distStr);
	}

}
