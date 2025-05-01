package net.unitego.lobecorp.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.access.DataAccess;
import net.unitego.lobecorp.data.SanityData;
import net.unitego.lobecorp.registry.AttributeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    LivingEntity lobeCorp$livingEntity = (LivingEntity) (Object) this;

    @Inject(method = "onAttributeUpdated", at = @At("HEAD"))
    private void onAttributeUpdatedMixin(Holder<Attribute> attribute, CallbackInfo ci) {
        if (lobeCorp$livingEntity instanceof Player player) {
            SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
            if (attribute.equals(AttributeRegistry.MAX_SANITY)) {
                float f = sanityData.getMaxSanity();
                if (sanityData.getSanity() > f) {
                    sanityData.setSanity(f);
                }
            } else if (attribute.equals(AttributeRegistry.MAX_ASSIMILATION)) {
                float f1 = sanityData.getMaxAssimilation();
                if (sanityData.getAssimilationAmount() > f1) {
                    sanityData.setAssimilationAmount(f1);
                }
            }
        }
    }
}
