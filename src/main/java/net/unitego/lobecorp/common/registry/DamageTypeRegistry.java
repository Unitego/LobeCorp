package net.unitego.lobecorp.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/03 11:01:51}
 */
public class DamageTypeRegistry {

    public static final DeferredRegister<DamageType> DAMAGE_TYPE = DeferredRegister.create(Registries.DAMAGE_TYPE, LobeCorp.MOD_ID);
    public static void init(IEventBus bus) {
        DAMAGE_TYPE.register(bus);
    }
}
