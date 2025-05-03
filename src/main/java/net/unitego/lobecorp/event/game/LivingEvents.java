package net.unitego.lobecorp.event.game;

import com.machinezoo.noexception.throwing.ThrowingConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.registry.tag.DamageTypeTagRegistry;

import java.util.function.Consumer;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 12:19:08}
 */
//@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class LivingEvents {
//    @SubscribeEvent
    public static void huntEvent(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        Entity entity = source.getEntity();
        if (!(entity instanceof Player player)) return;
        float amount = event.getAmount();
        SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
        float t = amount, t2 = amount;
        Consumer<Float> setA, setB, setC = null, setD = null;
        float a = 0.0F, b = 0.0F, c = 0.0F, d = 0.0F;

        if (source.is(DamageTypeTagRegistry.RED.get())) {
            checkAndSet(t, player.getAbsorptionAmount(), player::setAbsorptionAmount);
            t = Math.max(t - player.getAbsorptionAmount(), 0);
            setA = player::setAbsorptionAmount;
            a = player.getAbsorptionAmount() - (amount - t);
            setB = player::setHealth;
            b = player.getHealth() - t;
        }
        else if (source.is(DamageTypeTagRegistry.WHITE.get())) {
            checkAndSet(t, sanityData.getAssimilationAmount(), sanityData::setAssimilationAmount);
            t = Math.max(t - sanityData.getAssimilationAmount(), 0);
            setA = sanityData::setAssimilationAmount;
            setB = sanityData::setSanity;
        }
        else if (source.is(DamageTypeTagRegistry.BLACK.get())) {
            checkAndSet(t, player.getAbsorptionAmount(), player::setAbsorptionAmount);
            checkAndSet(t, sanityData.getAssimilationAmount(), sanityData::setAssimilationAmount);
            t = Math.max(t - player.getAbsorptionAmount(), 0);
            t2 = Math.max(t - sanityData.getAssimilationAmount(), 0);
            setA = player::setAbsorptionAmount;
            setB = sanityData::setAssimilationAmount;
            setC = player::setHealth;
            setD = sanityData::setSanity;
            a = player.getAbsorptionAmount() - (amount - t);
            b = sanityData.getAssimilationAmount() - (amount - t2);
            c = player.getHealth() - t;
            d = sanityData.getSanity() - t2;
        }
        else if (source.is(DamageTypeTagRegistry.PALE.get())) {
            t = (t / 100.0F) * player.getMaxHealth();
            checkAndSet(t, player.getAbsorptionAmount(), player::setAbsorptionAmount);
            t = Math.max(t - player.getAbsorptionAmount(), 0);
            setA = player::setAbsorptionAmount;
            setB = player::setHealth;
            a = player.getAbsorptionAmount() - ((amount / 100.0F) * player.getMaxHealth() - t);
            b = player.getHealth() - t;
        }
        else if (source.is(DamageTypeTagRegistry.SPIRIT.get())) {
            t = Math.max(t - player.getAbsorptionAmount(), 0);
            setA = sanityData::setAssimilationAmount;
            setB = sanityData::setSanity;
            a = player.getAbsorptionAmount() - ((amount / 100.0F) * player.getMaxHealth() - t);
            b = sanityData.getSanity() - t;
        }
        else if (source.is(DamageTypeTagRegistry.CENSORED.get())) {
            t = Math.max(t - player.getAbsorptionAmount(), 0);
            t2 = Math.max(t2 - sanityData.getAssimilationAmount(), 0);
            setA = player::setAbsorptionAmount;
            a = player.getAbsorptionAmount() - (amount - t);
            setB = sanityData::setAssimilationAmount;
            b =  sanityData.getAssimilationAmount() - (amount - t2);
            setC = player::setHealth;
            c = player.getHealth() - t;
            setD = sanityData::setSanity;
            d = sanityData.getSanity() - t2;
        } else {
            t = Math.max(t - player.getAbsorptionAmount(), 0);
            setA = player::setAbsorptionAmount;
            a = player.getAbsorptionAmount() - (amount - t);
            setB = player::setHealth;
            b = player.getHealth() - t;
        }
        setA(a, setA);
        setA(b, setB);
        setA(c, setC);
        setA(d, setD);
        event.setCanceled(true);

    }

    public static void checkAndSet(float v, float h, Consumer<Float> set) {
        if(v > h) set.accept(0.0F);
    }

    public static void setA(float v, Consumer<Float> set) {
        if(set != null) set.accept(v);
    }


}
