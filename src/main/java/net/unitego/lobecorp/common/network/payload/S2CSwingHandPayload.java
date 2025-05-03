package net.unitego.lobecorp.common.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

public record S2CSwingHandPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2CSwingHandPayload> TYPE =
            new CustomPacketPayload.Type<>(LobeCorp.rl("swing_hand"));

    public static final StreamCodec<FriendlyByteBuf, S2CSwingHandPayload> STREAM_CODEC =
            StreamCodec.unit(new S2CSwingHandPayload());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
