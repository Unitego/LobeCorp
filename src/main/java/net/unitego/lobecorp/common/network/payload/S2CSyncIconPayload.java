package net.unitego.lobecorp.common.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

public record S2CSyncIconPayload(String name, float value) implements CustomPacketPayload {
    public static final String HYDRATION = "hydration";
    public static final String DESICCATION = "desiccation";
    public static final String SATURATION = "saturation";
    public static final String EXHAUSTION = "exhaustion";
    public static final CustomPacketPayload.Type<S2CSyncIconPayload> TYPE =
            new CustomPacketPayload.Type<>(LobeCorp.rl("sync_icon"));

    public static final StreamCodec<FriendlyByteBuf, S2CSyncIconPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, S2CSyncIconPayload::name,
                    ByteBufCodecs.FLOAT, S2CSyncIconPayload::value,
                    S2CSyncIconPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
