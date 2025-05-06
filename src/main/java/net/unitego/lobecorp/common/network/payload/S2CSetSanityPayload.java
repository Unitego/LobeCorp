package net.unitego.lobecorp.common.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

public record S2CSetSanityPayload(float sanity, int waterLevel, float hydrationLevel
) implements CustomPacketPayload {
    public static final Type<S2CSetSanityPayload> TYPE = new Type<>(LobeCorp.rl("set_sanity"));

    public static final StreamCodec<FriendlyByteBuf, S2CSetSanityPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT, S2CSetSanityPayload::sanity,
                    ByteBufCodecs.VAR_INT, S2CSetSanityPayload::waterLevel,
                    ByteBufCodecs.FLOAT, S2CSetSanityPayload::hydrationLevel,
                    S2CSetSanityPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
