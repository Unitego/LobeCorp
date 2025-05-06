package net.unitego.lobecorp.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;

public class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, LobeCorp.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> SWALLOW_WATER = register("swallow_water");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHANGE_EQUIPMENT = register("change_equipment");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(LobeCorp.rl(name)));
    }
}
