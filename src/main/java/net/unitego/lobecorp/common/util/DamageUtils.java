package net.unitego.lobecorp.common.util;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.entity.abnormality.Abnormality;
import net.unitego.lobecorp.common.item.ego.EGOSuitItem;
import net.unitego.lobecorp.common.item.ego.EGOWeaponItem;
import net.unitego.lobecorp.common.manager.SanityManager;
import net.unitego.lobecorp.common.tag.DamageTypeTags;

public class DamageUtils {
    //处理玩家受伤
    public static void handlePlayerHurt(Player defender, DamageSource source, float rawAmount) {
        Entity attacker = source.getEntity();
        ResistResult resistResult = calculateRankResist(attacker, defender);
        SanityManager sanityManager = ((ManagerAccess) defender).lobeCorp$getSanityManager();
        if (source.is(DamageTypeTags.CENSORED_DAMAGE_TYPE.get()) || source.is(DamageTypeTags.DEPLETED_DAMAGE_TYPE.get())) {
            float healthAmount = rawAmount;
            float sanityAmount = rawAmount;
            healthAmount = Math.max(healthAmount - defender.getAbsorptionAmount(), 0);
            sanityAmount = Math.max(sanityAmount - sanityManager.getAssimilationAmount(), 0);
            defender.setAbsorptionAmount(defender.getAbsorptionAmount() - (rawAmount - healthAmount));
            sanityManager.setAssimilationAmount(sanityManager.getAssimilationAmount() - (rawAmount - sanityAmount));
            defender.setHealth(defender.getHealth() - healthAmount);
            sanityManager.setSanity(sanityManager.getSanity() - sanityAmount);
        } else if (source.is(DamageTypeTags.SPIRIT_DAMAGE_TYPE.get())) {
            float sanityAmount = rawAmount;
            sanityAmount = Math.max(sanityAmount - sanityManager.getAssimilationAmount(), 0);
            sanityManager.setAssimilationAmount(sanityManager.getAssimilationAmount() - (rawAmount - sanityAmount));
            sanityManager.setSanity(sanityManager.getSanity() - sanityAmount);
        } else if (source.is(DamageTypeTags.RED_DAMAGE_TYPE.get())) {
            float healthAmount = rawAmount * resistResult.redResist * resistResult.rankResist;
            if (healthAmount > defender.getAbsorptionAmount()) defender.setAbsorptionAmount(0);
            if (resistResult.redResist > 0) {
                healthAmount = Math.max(healthAmount - defender.getAbsorptionAmount(), 0);
                defender.setAbsorptionAmount(defender.getAbsorptionAmount() - (rawAmount * resistResult.redResist * resistResult.rankResist - healthAmount));
            }
            defender.setHealth(defender.getHealth() - healthAmount);
            if (resistResult.redResist > 1) {
                LowResistSlowUtils.apply(defender);
            }
        } else if (source.is(DamageTypeTags.WHITE_DAMAGE_TYPE.get())) {
            float sanityAmount = rawAmount * resistResult.whiteResist * resistResult.rankResist;
            if (sanityAmount > sanityManager.getAssimilationAmount()) sanityManager.setAssimilationAmount(0);
            if (resistResult.whiteResist > 0) {
                sanityAmount = Math.max(sanityAmount - sanityManager.getAssimilationAmount(), 0);
                sanityManager.setAssimilationAmount(sanityManager.getAssimilationAmount() - (rawAmount * resistResult.whiteResist * resistResult.rankResist - sanityAmount));
            }
            if (sanityManager.isPanicOrCrazing()) {
                if (sanityManager.isShouldKill()) {
                    defender.setHealth(defender.getHealth() - rawAmount);
                } else {
                    if (attacker instanceof Player player && ((ManagerAccess) player).lobeCorp$getSanityManager().isNormal()) {
                        sanityManager.setSanity(sanityManager.getSanity() + sanityAmount);
                    }
                }
            } else sanityManager.setSanity(sanityManager.getSanity() - sanityAmount);
            if (resistResult.whiteResist > 1) {
                LowResistSlowUtils.apply(defender);
            }
        } else if (source.is(DamageTypeTags.BLACK_DAMAGE_TYPE.get())) {
            float healthAmount = rawAmount * resistResult.blackResist * resistResult.rankResist;
            float sanityAmount = rawAmount * resistResult.blackResist * resistResult.rankResist;
            if (healthAmount > defender.getAbsorptionAmount()) defender.setAbsorptionAmount(0);
            if (sanityAmount > sanityManager.getAssimilationAmount()) sanityManager.setAssimilationAmount(0);
            if (resistResult.blackResist > 0) {
                healthAmount = Math.max(healthAmount - defender.getAbsorptionAmount(), 0);
                sanityAmount = Math.max(sanityAmount - sanityManager.getAssimilationAmount(), 0);
                defender.setAbsorptionAmount(defender.getAbsorptionAmount() - (rawAmount * resistResult.blackResist * resistResult.rankResist - healthAmount));
                sanityManager.setAssimilationAmount(sanityManager.getAssimilationAmount() - (rawAmount * resistResult.blackResist * resistResult.rankResist - sanityAmount));
            }
            defender.setHealth(defender.getHealth() - healthAmount);
            if (sanityManager.isPanicOrCrazing()) {
                if (attacker instanceof Player player && ((ManagerAccess) player).lobeCorp$getSanityManager().isNormal()) {
                    sanityManager.setSanity(sanityManager.getSanity() + sanityAmount);
                }
            } else sanityManager.setSanity(sanityManager.getSanity() - sanityAmount);
            if (resistResult.blackResist > 1) {
                LowResistSlowUtils.apply(defender);
            }
        } else if (source.is(DamageTypeTags.PALE_DAMAGE_TYPE.get())) {
            float healthAmount = (rawAmount / 100.0f) * defender.getMaxHealth() * resistResult.paleResist * resistResult.rankResist;
            if (healthAmount > defender.getAbsorptionAmount()) defender.setAbsorptionAmount(0);
            if (resistResult.paleResist > 0) {
                healthAmount = Math.max(healthAmount - defender.getAbsorptionAmount(), 0);
                defender.setAbsorptionAmount(defender.getAbsorptionAmount() - ((rawAmount / 100.0f) * defender.getMaxHealth() * resistResult.paleResist * resistResult.rankResist - healthAmount));
            }
            defender.setHealth(defender.getHealth() - healthAmount);
            if (resistResult.paleResist > 1) {
                LowResistSlowUtils.apply(defender);
            }
        } else {
            float healthAmount = rawAmount;
            healthAmount = Math.max(healthAmount - defender.getAbsorptionAmount(), 0);
            defender.setAbsorptionAmount(defender.getAbsorptionAmount() - (rawAmount - healthAmount));
            defender.setHealth(defender.getHealth() - healthAmount);
        }
    }

    //处理生物受伤
    public static void handleLivingHurt(LivingEntity living, DamageSource source, float rawAmount) {
        ResistResult resistResult = calculateRankResist(source.getEntity(), living);
        if (source.is(DamageTypeTags.CENSORED_DAMAGE_TYPE.get()) || source.is(DamageTypeTags.SPIRIT_DAMAGE_TYPE.get())) {
            float healthAmount = rawAmount;
            healthAmount = Math.max(healthAmount - living.getAbsorptionAmount(), 0);
            living.setAbsorptionAmount(living.getAbsorptionAmount() - (rawAmount - healthAmount));
            living.setHealth(living.getHealth() - healthAmount);
        } else if (source.is(DamageTypeTags.RED_DAMAGE_TYPE.get())) {
            float healthAmount = rawAmount * resistResult.redResist * resistResult.rankResist;
            if (resistResult.redResist > 0) {
                healthAmount = Math.max(healthAmount - living.getAbsorptionAmount(), 0);
                living.setAbsorptionAmount(living.getAbsorptionAmount() - (rawAmount * resistResult.redResist * resistResult.rankResist - healthAmount));
            }
            living.setHealth(living.getHealth() - healthAmount);
        } else if (source.is(DamageTypeTags.WHITE_DAMAGE_TYPE.get())) {
            float healthAmount = rawAmount * resistResult.whiteResist * resistResult.rankResist;
            if (resistResult.whiteResist > 0) {
                healthAmount = Math.max(healthAmount - living.getAbsorptionAmount(), 0);
                living.setAbsorptionAmount(living.getAbsorptionAmount() - (rawAmount * resistResult.whiteResist * resistResult.rankResist - healthAmount));
            }
            living.setHealth(living.getHealth() - healthAmount);
        } else if (source.is(DamageTypeTags.BLACK_DAMAGE_TYPE.get())) {
            float healthAmount = rawAmount * resistResult.blackResist * resistResult.rankResist;
            if (resistResult.blackResist > 0) {
                healthAmount = Math.max(healthAmount - living.getAbsorptionAmount(), 0);
                living.setAbsorptionAmount(living.getAbsorptionAmount() - (rawAmount * resistResult.blackResist * resistResult.rankResist - healthAmount));
            }
            living.setHealth(living.getHealth() - healthAmount);
        } else if (source.is(DamageTypeTags.PALE_DAMAGE_TYPE.get())) {
            float healthAmount = (rawAmount / 100) * living.getMaxHealth() * resistResult.paleResist * resistResult.rankResist;
            if (resistResult.paleResist > 0) {
                healthAmount = Math.max(healthAmount - living.getAbsorptionAmount(), 0);
                living.setAbsorptionAmount(living.getAbsorptionAmount() - ((rawAmount / 100) * living.getMaxHealth() * resistResult.paleResist * resistResult.rankResist - healthAmount));
            }
            living.setHealth(living.getHealth() - healthAmount);
        } else {
            float healthAmount = rawAmount;
            healthAmount = Math.max(healthAmount * (living instanceof Abnormality ? 0 : 1) - living.getAbsorptionAmount(), 0);
            living.setAbsorptionAmount(living.getAbsorptionAmount() - (rawAmount - healthAmount));
            living.setHealth(living.getHealth() - healthAmount);
        }
    }

    //等级抗性和四色抗性计算
    public static ResistResult calculateRankResist(Entity attacker, LivingEntity defender) {
        //初始化等级为ZAYIN和四色抗性为2.0f
        EGORank attackerRank = EGORank.ZAYIN, defenderRank = EGORank.ZAYIN;
        float redResist = 2.0f, whiteResist = 2.0f, blackResist = 2.0f, paleResist = 2.0f;
        //受击者等级获取
        if (defender instanceof Player player) {//如果受击者是玩家就获取玩家护甲位的护甲等级和四色抗性
            if (MiscUtils.getLobeCorpStack(player, LobeCorpEquipmentSlot.LOBECORP_SUIT).getItem() instanceof EGOSuitItem egoSuitItem) {
                defenderRank = egoSuitItem.getEGORank();
                redResist = egoSuitItem.getRedResist();
                whiteResist = egoSuitItem.getWhiteResist();
                blackResist = egoSuitItem.getBlackResist();
                paleResist = egoSuitItem.getPaleResist();
            }
        } else if (defender instanceof Abnormality abnormality) {//如果受击者是异想体，就获取异想体的等级和四色抗性
            defenderRank = abnormality.getEGORank();
            redResist = abnormality.getRedResist();
            whiteResist = abnormality.getWhiteResist();
            blackResist = abnormality.getBlackResist();
            paleResist = abnormality.getPaleResist();
        }
        //攻击者等级获取
        if (attacker instanceof Player player) {//如果攻击者是玩家就获取玩家主手位的武器等级
            if (player.getMainHandItem().getItem() instanceof EGOWeaponItem egoWeaponItem) {
                attackerRank = egoWeaponItem.getEGORank();
            }
        } else if (attacker instanceof Abnormality abnormality) {//如果攻击者是异想体，就获取异想体的等级
            attackerRank = abnormality.getEGORank();
        }
        //返回等级抗性和四色抗性记录
        return new ResistResult(attackerRank.calculateSuppression(defenderRank), redResist, whiteResist, blackResist, paleResist);
    }

    //处理玩家攻击
    public static void handlePlayerAttack(Entity target, Player player, EGOWeaponItem egoWeaponItem) {
        if (target.isAttackable()) {
            //绕过盔甲甲等这类东西
            if (!target.skipAttackInteraction(player)) {
                float f = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                float f1 = EnchantmentHelper.getDamageBonus(player.getMainHandItem(), target.getType());
                float f2 = player.getAttackStrengthScale(0.5F);
                f *= 0.2F + f2 * f2 * 0.8F;
                f1 *= f2;
                //击退例如恶魂火球之类的玩意儿
                if (target.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) && target instanceof Projectile projectile) {
                    projectile.deflect(ProjectileDeflection.AIM_DEFLECT, player, player, true);
                } else {
                    if (f > 0.0F || f1 > 0.0F) {
                        boolean flag = f2 > 0.9F;
                        float i = (float) player.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
                        i += EnchantmentHelper.getKnockbackBonus(player);
                        if (player.isSprinting() && flag) {
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, player.getSoundSource(), 1.0F, 1.0F);
                            i++;
                        }
                        f += player.getItemInHand(InteractionHand.MAIN_HAND).getItem().getAttackDamageBonus(player, f);
                        //处理暴击
                        boolean flag2 = flag && player.fallDistance > 0.0F && !player.onGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && target instanceof LivingEntity && !player.isSprinting();
                        var critEvent = CommonHooks.fireCriticalHit(player, target, flag2, flag2 ? 1.5F : 1.0F);
                        flag2 = critEvent.isCriticalHit();
                        if (flag2) {
                            f *= critEvent.getDamageMultiplier();
                        }
                        f += f1;
                        boolean flag3 = false;
                        //处理火焰附加附魔时间
                        float f4 = 0.0F;
                        boolean flag4 = false;
                        int j = EnchantmentHelper.getFireAspect(player);
                        if (target instanceof LivingEntity) {
                            f4 = ((LivingEntity) target).getHealth();
                            if (j > 0 && !target.isOnFire()) {
                                flag4 = true;
                                target.igniteForSeconds(1);
                            }
                        }
                        Vec3 vec3 = target.getDeltaMovement();
                        boolean flag5 = false;
                        // 蓄力不足则空挥退出
                        if (f2 < 1.0f) {
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, player.getSoundSource(), 1.0F, 1.0F);
                            player.resetAttackStrengthTicker();
                            return;
                        }
                        //造成伤害
                        for (ResourceKey<DamageType> damageType : egoWeaponItem.getDamageTypes()) {
                            target.invulnerableTime = 0;
                            boolean success = target.hurt(player.damageSources().source(damageType, player), f);
                            flag5 |= success;
                        }
                        //如果造成伤害
                        if (flag5) {
                            //处理击退
                            if (i > 0) {
                                if (target instanceof LivingEntity) {
                                    ((LivingEntity) target).knockback(i * 0.5F, Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)));
                                } else {
                                    target.push(-Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)) * i * 0.5F, 0.1, Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)) * i * 0.5F);
                                }
                                player.setDeltaMovement(player.getDeltaMovement().multiply(0.6, 1.0, 0.6));
                                player.setSprinting(false);
                            }
                            if (target instanceof ServerPlayer && target.hurtMarked) {
                                ((ServerPlayer) target).connection.send(new ClientboundSetEntityMotionPacket(target));
                                target.hurtMarked = false;
                                target.setDeltaMovement(vec3);
                            }
                            //处理暴击声音和粒子
                            if (flag2) {
                                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, player.getSoundSource(), 1.0F, 1.0F);
                                player.crit(target);
                            }
                            //处理击退声音
                            if (!flag2 && !flag3) {
                                if (flag) {
                                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, player.getSoundSource(), 1.0F, 1.0F
                                    );
                                } else {
                                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, player.getSoundSource(), 1.0F, 1.0F);
                                }
                            }
                            //处理附魔粒子
                            if (f1 > 0.0F) {
                                player.magicCrit(target);
                            }
                            //荆棘反伤和处理类似于末影龙这种多部分的实体
                            player.setLastHurtMob(target);
                            if (target instanceof LivingEntity) {
                                EnchantmentHelper.doPostHurtEffects((LivingEntity) target, player);
                            }
                            EnchantmentHelper.doPostDamageEffects(player, target);
                            Entity entity = target;
                            if (target instanceof PartEntity) {
                                entity = ((PartEntity<?>) target).getParent();
                            }
                            //处理攻击时物品耐久减少
                            if (!player.level().isClientSide && !player.getMainHandItem().isEmpty() && entity instanceof LivingEntity) {
                                ItemStack copy = player.getMainHandItem().copy();
                                player.getMainHandItem().hurtEnemy((LivingEntity) entity, player);
                                if (player.getMainHandItem().isEmpty()) {
                                    EventHooks.onPlayerDestroyItem(player, copy, InteractionHand.MAIN_HAND);
                                    player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                                }
                            }
                            //伤害统计和火焰和伤害粒子
                            if (target instanceof LivingEntity) {
                                float f5 = f4 - ((LivingEntity) target).getHealth();
                                player.awardStat(Stats.DAMAGE_DEALT, Math.round(f5 * 10.0F));
                                if (j > 0) {
                                    target.igniteForSeconds(j * 4);
                                }
                                if (player.level() instanceof ServerLevel && f5 > 2.0F) {
                                    int k = (int) ((double) f5 * 0.5);
                                    ((ServerLevel) player.level()).sendParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getY(0.5), target.getZ(), k, 0.1, 0.0, 0.1, 0.2);
                                }
                            }
                            player.causeFoodExhaustion(0.1F);
                        } else {
                            //没造成伤害时候播放击空音效还有灭火
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_NODAMAGE, player.getSoundSource(), 1.0F, 1.0F);
                            if (flag4) {
                                target.clearFire();
                            }
                        }
                    }
                }
                //重置攻击强度，在这里保证正确
                player.resetAttackStrengthTicker();
            }
        }
    }

    public record ResistResult(float rankResist, float redResist, float whiteResist, float blackResist,
                               float paleResist) {
    }
}
