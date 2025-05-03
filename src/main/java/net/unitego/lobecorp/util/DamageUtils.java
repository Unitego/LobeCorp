package net.unitego.lobecorp.util;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.registry.tag.DamageTypeTagRegistry;

public class DamageUtils {
    //处理玩家伤害
    public static void handlePlayerDamage(Player player, DamageSource source, float amount) {
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

    //处理非玩家伤害
    public static void handleNonPlayerDamage(LivingEntity living, DamageSource source, float amount) {
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
