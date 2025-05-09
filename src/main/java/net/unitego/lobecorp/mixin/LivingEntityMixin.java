package net.unitego.lobecorp.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.SanityManager;
import net.unitego.lobecorp.common.util.DamageUtils;
import net.unitego.lobecorp.registry.AttributesRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    final
    LivingEntity lobeCorp$livingEntity = (LivingEntity) (Object) this;

    @Shadow
    public abstract float getAbsorptionAmount();

    @Shadow
    public abstract float getHealth();

    //钳制精神值和认知同化值，让他们小于等于对应的最大值
    @Inject(method = "onAttributeUpdated", at = @At("HEAD"))
    private void onAttributeUpdatedMixin(Holder<Attribute> attribute, CallbackInfo ci) {
        if (lobeCorp$livingEntity instanceof Player player) {
            SanityManager sanityManager = ((ManagerAccess) player).lobeCorp$getSanityManager();
            if (attribute.equals(AttributesRegistry.MAX_SANITY)) {
                float maxSanity = sanityManager.getMaxSanity();
                if (sanityManager.getSanity() > maxSanity) {
                    sanityManager.setSanity(maxSanity);
                }
            } else if (attribute.equals(AttributesRegistry.MAX_ASSIMILATION)) {
                float maxAssimilation = sanityManager.getMaxAssimilation();
                if (sanityManager.getAssimilationAmount() > maxAssimilation) {
                    sanityManager.setAssimilationAmount(maxAssimilation);
                }
            }
        }
    }

    //废除原版伤害吸收机制
    @ModifyArg(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setAbsorptionAmount(F)V", ordinal = 0))
    private float applyDamageMixin1(float absorptionAmount) {
        return getAbsorptionAmount();
    }

    //脑叶公司受伤机制
    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V"))
    private void actuallyHurtMixin(DamageSource damageSource, float damageAmount, CallbackInfo ci) {
        DamageUtils.handleLivingHurt(lobeCorp$livingEntity, damageSource, damageAmount);
    }

    //废除原版减少生命值机制
    @ModifyArg(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setHealth(F)V"))
    private float actuallyHurtMixin(float health) {
        return getHealth();
    }

    //废除原版减少伤害吸收值机制
    @ModifyArg(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setAbsorptionAmount(F)V", ordinal = 1))
    private float applyDamageMixin2(float absorptionAmount) {
        return getAbsorptionAmount();
    }
}
