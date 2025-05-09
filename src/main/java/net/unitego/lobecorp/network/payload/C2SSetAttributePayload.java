package net.unitego.lobecorp.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public record C2SSetAttributePayload(Map<String, Double> values) implements CustomPacketPayload {
    public static final Type<C2SSetAttributePayload> TYPE = new Type<>(LobeCorp.rl("set_attribute"));

    public static final StreamCodec<FriendlyByteBuf, C2SSetAttributePayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> {
                        Map<String, Double> map = payload.values();
                        buf.writeVarInt(map.size());
                        for (Map.Entry<String, Double> entry : map.entrySet()) {
                            buf.writeUtf(entry.getKey());
                            buf.writeDouble(entry.getValue());
                        }
                    },
                    buf -> {
                        int size = buf.readVarInt();
                        Map<String, Double> map = new HashMap<>();
                        for (int i = 0; i < size; i++) {
                            String key = buf.readUtf();
                            double value = buf.readDouble();
                            map.put(key, value);
                        }
                        return new C2SSetAttributePayload(map);
                    }
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
