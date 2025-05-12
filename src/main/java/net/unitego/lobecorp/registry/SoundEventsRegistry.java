package net.unitego.lobecorp.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;

public class SoundEventsRegistry {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister
            .create(Registries.SOUND_EVENT, LobeCorp.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SWALLOW_WATER = register("swallow_water");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHANGE_EQUIPMENT = register("change_equipment");
    public static final DeferredHolder<SoundEvent, SoundEvent> SWITCH_WEAPON = register("switch_weapon");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(LobeCorp.rl(name)));
    }

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
