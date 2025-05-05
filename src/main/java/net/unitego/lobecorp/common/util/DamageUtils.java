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
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponItem;
import net.unitego.lobecorp.common.registry.tag.DamageTypeTagRegistry;

public class DamageUtils {
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

    //处理玩家受伤
    public static void handlePlayerHurt(Player player, DamageSource source, float amount) {
        SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
        if (source.is(DamageTypeTagRegistry.CENSORED_DAMAGE_TYPE.get())) {
            handleCensoredDamage(player, amount, sanityData);
        } else if (source.is(DamageTypeTagRegistry.SPIRIT_DAMAGE_TYPE.get())) {
            handleSpiritDamage(player, amount, sanityData);
        } else if (source.is(DamageTypeTagRegistry.RED_DAMAGE_TYPE.get())) {
            handleRedDamage(player, amount, sanityData);
        } else if (source.is(DamageTypeTagRegistry.WHITE_DAMAGE_TYPE.get())) {
            handleWhiteDamage(player, amount, sanityData);
        } else if (source.is(DamageTypeTagRegistry.BLACK_DAMAGE_TYPE.get())) {
            handleBlackDamage(player, amount, sanityData);
        } else if (source.is(DamageTypeTagRegistry.PALE_DAMAGE_TYPE.get())) {
            handlePaleDamage(player, amount, sanityData);
        } else {
            handleVanillaDamage(player, amount, sanityData);
        }
    }

    //处理非玩家受伤
    public static void handleNonPlayerHurt(LivingEntity living, DamageSource source, float amount) {
        float health = amount;
        health = Math.max(health - living.getAbsorptionAmount(), 0);
        living.setAbsorptionAmount(living.getAbsorptionAmount() - (amount - health));
        living.setHealth(living.getHealth() - health);
    }

    private static void handleVanillaDamage(Player player, float amount, SanityData sanityData) {
        float health = amount;
        health = Math.max(health - player.getAbsorptionAmount(), 0);
        player.setAbsorptionAmount(player.getAbsorptionAmount() - (amount - health));
        player.setHealth(player.getHealth() - health);
    }

    private static void handleRedDamage(Player player, float amount, SanityData sanityData) {
        float health = amount;
        if (health > player.getAbsorptionAmount()) player.setAbsorptionAmount(0);
        health = Math.max(health - player.getAbsorptionAmount(), 0);
        player.setAbsorptionAmount(player.getAbsorptionAmount() - (amount - health));
        player.setHealth(player.getHealth() - health);
    }

    private static void handleWhiteDamage(Player player, float amount, SanityData sanityData) {
        float sanity = amount;
        if (sanity > sanityData.getAssimilationAmount()) sanityData.setAssimilationAmount(0);
        sanity = Math.max(sanity - sanityData.getAssimilationAmount(), 0);
        sanityData.setAssimilationAmount(sanityData.getAssimilationAmount() - (amount - sanity));
        sanityData.setSanity(sanityData.getSanity() - sanity);
    }

    private static void handleBlackDamage(Player player, float amount, SanityData sanityData) {
        float sanity = amount;
        float health = amount;
        if (sanity > sanityData.getAssimilationAmount()) sanityData.setAssimilationAmount(0);
        if (health > player.getAbsorptionAmount()) player.setAbsorptionAmount(0);
        sanity = Math.max(sanity - sanityData.getAssimilationAmount(), 0);
        health = Math.max(health - player.getAbsorptionAmount(), 0);
        sanityData.setAssimilationAmount(sanityData.getAssimilationAmount() - (amount - sanity));
        player.setAbsorptionAmount(player.getAbsorptionAmount() - (amount - health));
        sanityData.setSanity(sanityData.getSanity() - sanity);
        player.setHealth(player.getHealth() - health);
    }

    private static void handlePaleDamage(Player player, float amount, SanityData sanityData) {
        float health = (amount / 100.0f) * player.getMaxHealth();
        if (health > player.getAbsorptionAmount()) player.setAbsorptionAmount(0);
        health = Math.max(health - player.getAbsorptionAmount(), 0);
        player.setAbsorptionAmount(player.getAbsorptionAmount() - ((amount / 100.0f) * player.getMaxHealth() - health));
        player.setHealth(player.getHealth() - health);
    }

    private static void handleSpiritDamage(Player player, float amount, SanityData sanityData) {
        float sanity = amount;
        sanity = Math.max(sanity - sanityData.getAssimilationAmount(), 0);
        sanityData.setAssimilationAmount(sanityData.getAssimilationAmount() - (amount - sanity));
        sanityData.setSanity(sanityData.getSanity() - sanity);
    }

    private static void handleCensoredDamage(Player player, float amount, SanityData sanityData) {
        float sanity = amount;
        float health = amount;
        sanity = Math.max(sanity - sanityData.getAssimilationAmount(), 0);
        health = Math.max(health - player.getAbsorptionAmount(), 0);
        sanityData.setAssimilationAmount(sanityData.getAssimilationAmount() - (amount - sanity));
        player.setAbsorptionAmount(player.getAbsorptionAmount() - (amount - health));
        sanityData.setSanity(sanityData.getSanity() - sanity);
        player.setHealth(player.getHealth() - health);
    }
}
