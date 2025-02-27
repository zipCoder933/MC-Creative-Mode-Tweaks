package org.zipcoder.cmt.mixin.fastPlace;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {

    /**
     * Tells minecraft to place a block, set an entity or some building action
     */
    @Invoker
    void callStartUseItem();
}
