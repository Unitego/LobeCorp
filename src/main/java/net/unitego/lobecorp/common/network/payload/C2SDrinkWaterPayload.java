package net.unitego.lobecorp.common.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

public record C2SDrinkWaterPayload(String waterSource) implements CustomPacketPayload {
    public static final String STREAM = "stream";
    public static final String RAIN = "rain";
    public static final String CAULDRON = "cauldron";

    public static final CustomPacketPayload.Type<C2SDrinkWaterPayload> TYPE =
            new CustomPacketPayload.Type<>(LobeCorp.rl("drink_water"));

    public static final StreamCodec<FriendlyByteBuf, C2SDrinkWaterPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, C2SDrinkWaterPayload::waterSource,
                    C2SDrinkWaterPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
