package net.unitego.lobecorp.mixin;

import net.minecraft.world.effect.HungerMobEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HungerMobEffect.class)
public abstract class HungerMobEffectMixin {
    @Redirect(method = "applyEffectTick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"))
    private void applyEffectTickMixin(Player instance, float exhaustion) {
        if (!instance.getAbilities().invulnerable) {
            if (!instance.level().isClientSide) {
                instance.getFoodData().addExhaustion(exhaustion);
            }
        }
    }
}
