package net.unitego.lobecorp.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.registry.ModAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    final
    LivingEntity lobeCorp$livingEntity = (LivingEntity) (Object) this;

    //钳制精神值和认知同化值，让他们小于等于对应的最大值
    @Inject(method = "onAttributeUpdated", at = @At("HEAD"))
    private void onAttributeUpdatedMixin(Holder<Attribute> attribute, CallbackInfo ci) {
        if (lobeCorp$livingEntity instanceof Player player) {
            SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
            if (attribute.equals(ModAttributes.MAX_SANITY)) {
                float maxSanity = sanityData.getMaxSanity();
                if (sanityData.getSanity() > maxSanity) {
                    sanityData.setSanity(maxSanity);
                }
            } else if (attribute.equals(ModAttributes.MAX_ASSIMILATION)) {
                float maxAssimilation = sanityData.getMaxAssimilation();
                if (sanityData.getAssimilationAmount() > maxAssimilation) {
                    sanityData.setAssimilationAmount(maxAssimilation);
                }
            }
        }
    }
}
