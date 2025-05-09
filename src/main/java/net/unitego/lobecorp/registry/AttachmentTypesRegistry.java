package net.unitego.lobecorp.registry;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.unitego.lobecorp.LobeCorp;

import java.util.function.Supplier;

public class AttachmentTypesRegistry {
    public static final DeferredRegister<AttachmentType<?>> REGISTER = DeferredRegister
            .create(NeoForgeRegistries.ATTACHMENT_TYPES, LobeCorp.MOD_ID);

    public static final Supplier<AttachmentType<ItemStackHandler>> LOBECORP_STACK = REGISTER.register(
            "lobecorp_stack", () -> AttachmentType.serializable(() -> new ItemStackHandler(4)).build());

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
