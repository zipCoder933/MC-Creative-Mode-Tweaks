package org.zipcoder.cmt.client.keys;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class KeyBase extends KeyMapping {

	protected int pressTime = 0;
	protected int doubleTapTick = 0;
	private static final int DOUBLE_TAP_MAX = 5;
	
	public KeyBase(String description, int keyCode, String category) {
		super(description, keyCode, category);
	}

	@SubscribeEvent
	public void onKeyInput(final InputEvent.Key event) {
		//Mark this key as handled.
	}

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.getPhase() == EventPriority.NORMAL) {
			if (this.isDown()) {
				this.pressTime = Math.min(12345, this.pressTime + 1);
				if (this.pressTime == 1) {
					this.onKeyPressStart();
					this.doubleTapTick = DOUBLE_TAP_MAX;
				}
				whilePressed();
			} else {
				if (this.pressTime > 0) {
					this.onKeyReleased();
					this.pressTime = 0;
				}
				if (this.doubleTapTick > 0) {
					this.doubleTapTick--;
				}
			}
		}
	}

	protected void onKeyPressStart() {
	}
	
	protected void whilePressed() {
	}

	protected void onKeyReleased() {
	}

}