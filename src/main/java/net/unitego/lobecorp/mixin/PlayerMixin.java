package net.unitego.lobecorp.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.SanityManager;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.common.util.DamageUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements ManagerAccess {
    @Unique
    private static final EntityDataAccessor<Float> DATA_PLAYER_SANITY_ID = SynchedEntityData.defineId(PlayerMixin.class, EntityDataSerializers.FLOAT);
    @Unique
    private static final EntityDataAccessor<Float> DATA_PLAYER_ASSIMILATION_ID = SynchedEntityData.defineId(PlayerMixin.class, EntityDataSerializers.FLOAT);
    @Unique
    final
    Player lobeCorp$player = (Player) (Object) this;
    @Unique
    private final WaterManager lobeCorp$waterManager = new WaterManager(lobeCorp$player);
    @Unique
    private final SanityManager lobeCorp$sanityManager = new SanityManager(lobeCorp$player, DATA_PLAYER_SANITY_ID, DATA_PLAYER_ASSIMILATION_ID);
    @Unique
    private final StaffManager lobeCorp$staffManager = new StaffManager(lobeCorp$player);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract float getAbsorptionAmount();

    @Override
    public WaterManager lobeCorp$getWaterManager() {
        return lobeCorp$waterManager;
    }

    @Override
    public SanityManager lobeCorp$getSanityManager() {
        return lobeCorp$sanityManager;
    }

    @Override
    public StaffManager lobeCorp$getStaffManager() {
        return lobeCorp$staffManager;
    }

    //定义同步实体数据
    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineSynchedDataMixin(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_PLAYER_SANITY_ID, 1.0F);
        builder.define(DATA_PLAYER_ASSIMILATION_ID, 0.0F);
    }

    //更新机制
    @Inject(method = "tick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;tick(Lnet/minecraft/world/entity/player/Player;)V"))
    private void tickMixin(CallbackInfo ci) {
        lobeCorp$waterManager.tick();
    }

    //使精神值和干渴值能在和平模式且游戏规则自然恢复启用下回复
    @Inject(method = "aiStep", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getHealth()F", ordinal = 0))
    private void aiStepMixin2(CallbackInfo ci) {
        if (lobeCorp$sanityManager.getSanity() < lobeCorp$sanityManager.getMaxSanity() && lobeCorp$player.tickCount % 20 == 0) {
            lobeCorp$sanityManager.cure(1.0F);
        }

        if (lobeCorp$waterManager.needsWater() && tickCount % 10 == 0) {
            lobeCorp$waterManager.setWaterLevel(lobeCorp$waterManager.getWaterLevel() + 1);
        }
    }

    //读取持久化数据
    @Inject(method = "readAdditionalSaveData", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void readAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci) {
        lobeCorp$sanityManager.readAdditionalSaveData(compound);
        lobeCorp$waterManager.readAdditionalSaveData(compound);
    }

    //写入持久化数据
    @Inject(method = "addAdditionalSaveData", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V"))
    private void addAdditionalSaveDataMixin(CompoundTag compound, CallbackInfo ci) {
        lobeCorp$sanityManager.addAdditionalSaveData(compound);
        lobeCorp$waterManager.addAdditionalSaveData(compound);
    }

    //废除原版伤害吸收机制
    @ModifyArg(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setAbsorptionAmount(F)V"))
    private float actuallyHurtMixin1(float a) {
        return getAbsorptionAmount();
    }

    //脑叶公司受伤机制
    @Inject(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setHealth(F)V"))
    private void actuallyHurtMixin(DamageSource damageSrc, float damageAmount, CallbackInfo ci) {
        DamageUtils.handlePlayerHurt(lobeCorp$player, damageSrc, damageAmount);
    }

    //废除原版减少生命值机制
    @ModifyArg(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setHealth(F)V"))
    private float actuallyHurtMixin2(float par1) {
        return getHealth();
    }

    //干渴机制复用饥饿机制的消耗度
    @Inject(method = "causeFoodExhaustion", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V"))
    private void causeFoodExhaustionMixin(float exhaustion, CallbackInfo ci) {
        lobeCorp$waterManager.addDesiccation(exhaustion);
    }
}
