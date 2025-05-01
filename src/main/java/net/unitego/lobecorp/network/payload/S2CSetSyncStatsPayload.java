package net.unitego.lobecorp.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

public record S2CSetSyncStatsPayload(float sanity,
                                     int waterLevel, float hydrationLevel,
                                     float desiccationLevel, float exhaustionLevel
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2CSetSyncStatsPayload> TYPE =
            new CustomPacketPayload.Type<>(LobeCorp.rl("set_stats"));

    public static final StreamCodec<FriendlyByteBuf, S2CSetSyncStatsPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT, S2CSetSyncStatsPayload::sanity,
                    ByteBufCodecs.VAR_INT, S2CSetSyncStatsPayload::waterLevel,
                    ByteBufCodecs.FLOAT, S2CSetSyncStatsPayload::hydrationLevel,
                    ByteBufCodecs.FLOAT, S2CSetSyncStatsPayload::desiccationLevel,
                    ByteBufCodecs.FLOAT, S2CSetSyncStatsPayload::exhaustionLevel,
                    S2CSetSyncStatsPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
