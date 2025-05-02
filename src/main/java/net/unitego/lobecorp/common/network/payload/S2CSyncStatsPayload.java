package net.unitego.lobecorp.common.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record S2CSyncStatsPayload(String name, float value) implements CustomPacketPayload {
    public static final String HYDRATION = "hydration";
    public static final String DESICCATION = "desiccation";
    public static final String SATURATION = "saturation";
    public static final String EXHAUSTION = "exhaustion";
    public static final Map<UUID, Float> HYDRATIONS = new HashMap<>();
    public static final Map<UUID, Float> DESICCATIONS = new HashMap<>();
    public static final Map<UUID, Float> SATURATIONS = new HashMap<>();
    public static final Map<UUID, Float> EXHAUSTIONS = new HashMap<>();
    public static final CustomPacketPayload.Type<S2CSyncStatsPayload> TYPE =
            new CustomPacketPayload.Type<>(LobeCorp.rl("sync_stats"));

    public static final StreamCodec<FriendlyByteBuf, S2CSyncStatsPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, S2CSyncStatsPayload::name,
                    ByteBufCodecs.FLOAT, S2CSyncStatsPayload::value,
                    S2CSyncStatsPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
