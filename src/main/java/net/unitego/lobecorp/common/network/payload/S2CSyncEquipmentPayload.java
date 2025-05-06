package net.unitego.lobecorp.common.network.payload;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record S2CSyncEquipmentPayload(int id,
                                      List<Pair<LobeCorpEquipmentSlot, ItemStack>> list) implements CustomPacketPayload {
    public static final Type<S2CSyncEquipmentPayload> TYPE = new Type<>(LobeCorp.rl("sync_equipment"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CSyncEquipmentPayload> STREAM_CODEC =
            StreamCodec.of(
                    (buf, payload) -> {
                        buf.writeVarInt(payload.id());
                        List<Pair<LobeCorpEquipmentSlot, ItemStack>> list = payload.list();
                        buf.writeVarInt(list.size());
                        for (Pair<LobeCorpEquipmentSlot, ItemStack> pair : list) {
                            buf.writeVarInt(pair.getFirst().ordinal());
                            ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, pair.getSecond());
                        }
                    },
                    buf -> {
                        int serverPlayerId = buf.readVarInt();
                        int size = buf.readVarInt();
                        List<Pair<LobeCorpEquipmentSlot, ItemStack>> list = new ArrayList<>();
                        LobeCorpEquipmentSlot[] slots = LobeCorpEquipmentSlot.values();
                        for (int i = 0; i < size; i++) {
                            int ordinal = buf.readVarInt();
                            LobeCorpEquipmentSlot slot = slots[ordinal];
                            ItemStack stack = ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
                            list.add(Pair.of(slot, stack));
                        }
                        return new S2CSyncEquipmentPayload(serverPlayerId, list);
                    }
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
