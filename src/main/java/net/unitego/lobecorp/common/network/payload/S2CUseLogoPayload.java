package net.unitego.lobecorp.common.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

public record S2CUseLogoPayload() implements CustomPacketPayload {
    public static final Type<S2CUseLogoPayload> TYPE = new Type<>(LobeCorp.rl("use_logo"));

    public static final StreamCodec<FriendlyByteBuf, S2CUseLogoPayload> STREAM_CODEC =
            StreamCodec.unit(new S2CUseLogoPayload());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
