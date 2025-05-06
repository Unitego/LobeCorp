package net.unitego.lobecorp.common.registry;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.unitego.lobecorp.LobeCorp;

import java.util.function.Supplier;

public class ModAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, LobeCorp.MOD_ID);

    public static final Supplier<AttachmentType<ItemStackHandler>> LOBECORP_SLOTS = ATTACHMENT_TYPES.register(
            "lobecorp_slots", () -> AttachmentType.serializable(() -> new ItemStackHandler(4)).build());
}
