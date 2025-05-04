package net.unitego.lobecorp.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.data.StaffData;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.registry.ModAttributes;
import net.unitego.lobecorp.common.registry.SEDRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements DataAccess {
    @Unique
    final
    Player lobeCorp$player = (Player) (Object) this;
    @Unique
    private final WaterData lobeCorp$waterData = new WaterData();
    @Unique
    private final SanityData lobeCorp$sanityData = new SanityData(lobeCorp$player);
    @Unique
    private final StaffData lobeCorp$staffData = new StaffData(lobeCorp$player);
    @Unique
    private double lobeCorp$lastAttackVelocity = -1;
    @Unique
    private double lobeCorp$lastMoveVelocity = -1;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public WaterData lobeCorp$getWaterData() {
        return lobeCorp$waterData;
    }

    @Override
    public SanityData lobeCorp$getSanityData() {
        return lobeCorp$sanityData;
    }

    @Override
    public StaffData lobeCorp$getStaffData() {
        return lobeCorp$staffData;
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

    //让脑叶公司的攻击速率和移动速率去一定方式的修饰原版的攻击速度和移动速度
    @Inject(method = "aiStep", at = @At(value = "HEAD"))
    private void aiStepMixin1(CallbackInfo ci) {
        if (lobeCorp$player.level().isClientSide) return;
        StaffData staffData = ((DataAccess) lobeCorp$player).lobeCorp$getStaffData();
        double attackVelocity = staffData.getAttackVelocity();
        double moveVelocity = staffData.getMoveVelocity();
        //修改原版攻击速度
        AttributeInstance attackSpeed = lobeCorp$player.getAttribute(Attributes.ATTACK_SPEED);
        if (attackVelocity != lobeCorp$lastAttackVelocity && attackSpeed != null) {
            AttributeModifier modifier = attackSpeed.getModifier(ModAttributes.ATTACK_VELOCITY_MODIFIER_ID);
            if (modifier != null) attackSpeed.removeModifier(modifier);
            attackSpeed.addPermanentModifier(new AttributeModifier(ModAttributes.ATTACK_VELOCITY_MODIFIER_ID,
                    "LobeCorp Attack Velocity Modifier",
                    (attackVelocity * 0.2f) / 100, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            lobeCorp$lastAttackVelocity = attackVelocity;
        }
        // 修改原版移动速度
        AttributeInstance movementSpeed = lobeCorp$player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (moveVelocity != lobeCorp$lastMoveVelocity && movementSpeed != null) {
            AttributeModifier modifier = movementSpeed.getModifier(ModAttributes.MOVE_VELOCITY_MODIFIER_ID);
            if (modifier != null) movementSpeed.removeModifier(modifier);
            movementSpeed.addPermanentModifier(new AttributeModifier(ModAttributes.MOVE_VELOCITY_MODIFIER_ID,
                    "LobeCorp Move Velocity Modifier",
                    (moveVelocity * 0.2f) / 100, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            lobeCorp$lastMoveVelocity = moveVelocity;
        }
    }

    //使精神值和干渴值能在和平模式且游戏规则自然恢复启用下回复
    @Inject(method = "aiStep", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getHealth()F", ordinal = 0))
    private void aiStepMixin2(CallbackInfo ci) {
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
