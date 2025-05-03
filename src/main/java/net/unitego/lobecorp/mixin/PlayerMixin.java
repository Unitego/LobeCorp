package net.unitego.lobecorp.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.registry.ModAttributes;
import net.unitego.lobecorp.common.registry.SEDRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements DataAccess {
    @Unique
    private final WaterData lobeCorp$waterData = new WaterData();
    @Unique
    Player lobeCorp$player = (Player) (Object) this;
    @Unique
    private final SanityData lobeCorp$sanityData = new SanityData(lobeCorp$player);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    //为玩家附加最大精神值和最大认知同化值的属性
    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void createAttributesMixin(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue().add(ModAttributes.MAX_SANITY).add(ModAttributes.MAX_ASSIMILATION);
    }

    @Override
    public WaterData lobeCorp$getWaterData() {
        return lobeCorp$waterData;
    }

    @Override
    public SanityData lobeCorp$getSanityData() {
        return lobeCorp$sanityData;
    }

    //定义同步实体数据
    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineSynchedDataMixin(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(SEDRegistry.DATA_PLAYER_SANITY_ID, 1.0F);
        builder.define(SEDRegistry.DATA_PLAYER_ASSIMILATION_ID, 0.0F);
    }

    //更新机制
    @Inject(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;tick(Lnet/minecraft/world/entity/player/Player;)V"))
    private void tickMixin(CallbackInfo ci) {
        lobeCorp$waterData.tick(lobeCorp$player);
    }

    //使精神值和干渴值能在和平模式且游戏规则自然恢复启用下回复
    @Inject(method = "aiStep", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getHealth()F", ordinal = 0))
    private void aiStepMixin(CallbackInfo ci) {
        if (lobeCorp$sanityData.getSanity() < lobeCorp$sanityData.getMaxSanity() && lobeCorp$player.tickCount % 20 == 0) {
            lobeCorp$sanityData.cure(1.0F);
        }

        if (lobeCorp$waterData.needsWater() && tickCount % 10 == 0) {
            lobeCorp$waterData.setWaterLevel(lobeCorp$waterData.getWaterLevel() + 1);
        }
    }

    //读取持久化数据
    @Inject(method = "readAdditionalSaveData", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void readAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci) {
        lobeCorp$sanityData.readAdditionalSaveData(compound);
        lobeCorp$waterData.readAdditionalSaveData(compound);
    }

    //写入持久化数据
    @Inject(method = "addAdditionalSaveData", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void addAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci) {
        lobeCorp$sanityData.addAdditionalSaveData(compound);
        lobeCorp$waterData.addAdditionalSaveData(compound);
    }

    //干渴机制复用饥饿机制的消耗度
    @Inject(method = "causeFoodExhaustion", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"))
    private void causeFoodExhaustionMixin(float exhaustion, CallbackInfo ci) {
        lobeCorp$waterData.addDesiccation(exhaustion);
    }
}
