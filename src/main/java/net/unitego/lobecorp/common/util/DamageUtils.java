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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.item.ego.suit.EGOSuitItem;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponItem;
import net.unitego.lobecorp.common.registry.tag.DamageTypeTagRegistry;

public class DamageUtils {
    //处理玩家受伤
    public static void handlePlayerHurt(Player defender, DamageSource source, float amount) {
        //等级抗性和四色抗性计算
        Item defenderItem = LobeCorpUtils.getLobeCorpStack(defender, LobeCorpEquipmentSlot.LOBECORP_SUIT).getItem();
        Entity attacker = source.getEntity();
        EGORank attackerRank = EGORank.ZAYIN, defenderRank = EGORank.ZAYIN;
        float redResist = 2.0f, whiteResist = 2.0f, blackResist = 2.0f, paleResist = 2.0f;
        if (defenderItem instanceof EGOSuitItem egoSuitItem) {
            defenderRank = egoSuitItem.getEGORank();
            redResist = egoSuitItem.getRedResist();
            whiteResist = egoSuitItem.getWhiteResist();
            blackResist = egoSuitItem.getBlackResist();
            paleResist = egoSuitItem.getPaleResist();
        }
        if (attacker instanceof Player) {
            Item attackerItem = ((Player) attacker).getMainHandItem().getItem();
            if (attackerItem instanceof EGOWeaponItem egoWeaponItem) {
                attackerRank = egoWeaponItem.getEGORank();
            }
        }
        float rankResist = attackerRank.calculateSuppression(defenderRank);

        SanityData sanityData = ((DataAccess) defender).lobeCorp$getSanityData();
        if (source.is(DamageTypeTagRegistry.CENSORED_DAMAGE_TYPE.get())) {
            handleCensoredDamage(defender, sanityData, amount);
        } else if (source.is(DamageTypeTagRegistry.SPIRIT_DAMAGE_TYPE.get())) {
            handleSpiritDamage(sanityData, amount);
        } else if (source.is(DamageTypeTagRegistry.RED_DAMAGE_TYPE.get())) {
            handleRedDamage(defender, amount, redResist, rankResist);
        } else if (source.is(DamageTypeTagRegistry.WHITE_DAMAGE_TYPE.get())) {
            handleWhiteDamage(sanityData, amount, whiteResist, rankResist);
        } else if (source.is(DamageTypeTagRegistry.BLACK_DAMAGE_TYPE.get())) {
            handleBlackDamage(defender, sanityData, amount, blackResist, rankResist);
        } else if (source.is(DamageTypeTagRegistry.PALE_DAMAGE_TYPE.get())) {
            handlePaleDamage(defender, amount, paleResist, rankResist);
        } else {
            handleVanillaDamage(defender, amount);
        }
    }

    //处理非玩家受伤
    public static void handleNonPlayerHurt(LivingEntity living, DamageSource source, float amount) {
        float health = amount;
        health = Math.max(health - living.getAbsorptionAmount(), 0);
        living.setAbsorptionAmount(living.getAbsorptionAmount() - (amount - health));
        living.setHealth(living.getHealth() - health);
    }

    private static void handleVanillaDamage(Player defender, float amount) {
        float health = Math.max(amount - defender.getAbsorptionAmount(), 0);
        defender.setAbsorptionAmount(defender.getAbsorptionAmount() - (amount - health));
        defender.setHealth(defender.getHealth() - health);
    }

    private static void handleRedDamage(Player defender, float amount, float redResit, float rankResist) {
        float health = amount * redResit * rankResist;
        if (health > defender.getAbsorptionAmount()) defender.setAbsorptionAmount(0);
        if (redResit > 0) {
            health = Math.max(health - defender.getAbsorptionAmount(), 0);
            defender.setAbsorptionAmount(defender.getAbsorptionAmount() - (amount * redResit * rankResist - health));
        }
        defender.setHealth(defender.getHealth() - health);
    }

    private static void handleWhiteDamage(SanityData sanityData, float amount, float whiteResist, float rankResist) {
        float sanity = amount * whiteResist * rankResist;
        if (sanity > sanityData.getAssimilationAmount()) sanityData.setAssimilationAmount(0);
        if (whiteResist > 0) {
            sanity = Math.max(sanity - sanityData.getAssimilationAmount(), 0);
            sanityData.setAssimilationAmount(sanityData.getAssimilationAmount() - (amount * whiteResist * rankResist - sanity));
        }
        sanityData.setSanity(sanityData.getSanity() - sanity);
    }

    private static void handleBlackDamage(Player defender, SanityData sanityData, float amount, float blackResist, float rankResist) {
        float sanity = amount * blackResist * rankResist;
        float health = amount * blackResist * rankResist;
        if (sanity > sanityData.getAssimilationAmount()) sanityData.setAssimilationAmount(0);
        if (health > defender.getAbsorptionAmount()) defender.setAbsorptionAmount(0);
        if (blackResist > 0) {
            sanity = Math.max(sanity - sanityData.getAssimilationAmount(), 0);
            health = Math.max(health - defender.getAbsorptionAmount(), 0);
            sanityData.setAssimilationAmount(sanityData.getAssimilationAmount() - (amount * blackResist * rankResist - sanity));
            defender.setAbsorptionAmount(defender.getAbsorptionAmount() - (amount * blackResist * rankResist - health));
        }
        sanityData.setSanity(sanityData.getSanity() - sanity);
        defender.setHealth(defender.getHealth() - health);
    }

    private static void handlePaleDamage(Player defender, float amount, float paleResist, float rankResist) {
        float health = (amount / 100.0f) * defender.getMaxHealth() * paleResist * rankResist;
        if (health > defender.getAbsorptionAmount()) defender.setAbsorptionAmount(0);
        if (paleResist > 0) {
            health = Math.max(health - defender.getAbsorptionAmount(), 0);
            defender.setAbsorptionAmount(defender.getAbsorptionAmount() - ((amount / 100.0f) * defender.getMaxHealth() * paleResist * rankResist - health));
        }
        defender.setHealth(defender.getHealth() - health);
    }

    private static void handleSpiritDamage(SanityData sanityData, float amount) {
        float sanity = amount;
        sanity = Math.max(sanity - sanityData.getAssimilationAmount(), 0);
        sanityData.setAssimilationAmount(sanityData.getAssimilationAmount() - (amount - sanity));
        sanityData.setSanity(sanityData.getSanity() - sanity);
    }

    private static void handleCensoredDamage(Player defender, SanityData sanityData, float amount) {
        float sanity = amount;
        float health = amount;
        sanity = Math.max(sanity - sanityData.getAssimilationAmount(), 0);
        health = Math.max(health - defender.getAbsorptionAmount(), 0);
        sanityData.setAssimilationAmount(sanityData.getAssimilationAmount() - (amount - sanity));
        defender.setAbsorptionAmount(defender.getAbsorptionAmount() - (amount - health));
        sanityData.setSanity(sanityData.getSanity() - sanity);
        defender.setHealth(defender.getHealth() - health);
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
                        var critEvent = net.neoforged.neoforge.common.CommonHooks.fireCriticalHit(player, target, flag2, flag2 ? 1.5F : 1.0F);
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
                            if (target instanceof net.neoforged.neoforge.entity.PartEntity) {
                                entity = ((net.neoforged.neoforge.entity.PartEntity<?>) target).getParent();
                            }
                            //处理攻击时物品耐久减少
                            if (!player.level().isClientSide && !player.getMainHandItem().isEmpty() && entity instanceof LivingEntity) {
                                ItemStack copy = player.getMainHandItem().copy();
                                player.getMainHandItem().hurtEnemy((LivingEntity) entity, player);
                                if (player.getMainHandItem().isEmpty()) {
                                    net.neoforged.neoforge.event.EventHooks.onPlayerDestroyItem(player, copy, InteractionHand.MAIN_HAND);
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
}
