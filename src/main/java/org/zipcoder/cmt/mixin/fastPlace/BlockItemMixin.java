package org.zipcoder.cmt.mixin.fastPlace;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.zipcoder.cmt.client.fastPlace.SmartBlockPlacementClient;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    /**
     * Called whenever a block is placed
     * @param useOnContext
     * @param cir
     */
    @Inject(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BlockItem;place(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/InteractionResult;"))
    public void use(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        //whenever we place a block in any way, we reset the tick to allow the smart block placement to fire immediately after
        //SmartBlockPlacementClient.tickPlacement = 0;
    }
}
