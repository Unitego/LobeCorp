package net.unitego.lobecorp.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

public record S2CDrinkResetPayload() implements CustomPacketPayload {
    public static final Type<S2CDrinkResetPayload> TYPE = new Type<>(LobeCorp.rl("drink_reset"));

    public static final StreamCodec<FriendlyByteBuf, S2CDrinkResetPayload> STREAM_CODEC =
            StreamCodec.unit(new S2CDrinkResetPayload());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
