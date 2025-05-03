package net.unitego.lobecorp.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.loader.HydratingFoodLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    //允许玩家在饥饿值满，干渴值未满时食用回复干渴值和饱水度的食物
    @Inject(method = "use", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/InteractionResultHolder;fail(Ljava/lang/Object;)Lnet/minecraft/world/InteractionResultHolder;"), cancellable = true)
    private void useMixin(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        WaterData waterData = ((DataAccess) player).lobeCorp$getWaterData();
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (waterData.needsWater() && HydratingFoodLoader.get(itemInHand.getItem()).isPresent()) {
            player.startUsingItem(usedHand);
            cir.setReturnValue(InteractionResultHolder.consume(itemInHand));
        }
    }
}
