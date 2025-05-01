package net.unitego.lobecorp.registry;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.unitego.lobecorp.LobeCorp;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 20:32:36}
 */
public class AttachmentTypeRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, LobeCorp.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> PLAYER_SANITY = ATTACHMENT_TYPES.register("player_sanity", () -> AttachmentType.builder(() -> 1.0F).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Float>> PLAYER_ASSIMILATION = ATTACHMENT_TYPES.register("player_assimilation", () -> AttachmentType.builder(() -> 0.0F).build());

    public static void init(IEventBus bus) {
        ATTACHMENT_TYPES.register(bus);
    }
}
