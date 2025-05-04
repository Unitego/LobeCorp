package net.unitego.lobecorp.common.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

public record C2SOpenEquipmentPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<C2SOpenEquipmentPayload> TYPE =
            new CustomPacketPayload.Type<>(LobeCorp.rl("open_equipment"));

    public static final StreamCodec<FriendlyByteBuf, C2SOpenEquipmentPayload> STREAM_CODEC =
            StreamCodec.unit(new C2SOpenEquipmentPayload());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
