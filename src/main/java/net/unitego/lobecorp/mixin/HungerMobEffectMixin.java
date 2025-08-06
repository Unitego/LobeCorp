package net.unitego.lobecorp.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.effect.HungerMobEffect;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HungerMobEffect.class)
public abstract class HungerMobEffectMixin {

    //重定向饥饿效果，让他直接调用饥饿机制增加消耗度方法，防止饥饿效果能影响到干渴机制
    @WrapWithCondition(
            method = "applyEffectTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V")
    )
    private boolean applyEffectTickCondition(Player player, float exhaustion) {
        return !player.getAbilities().invulnerable && !player.level().isClientSide();
    }




}
