package net.unitego.lobecorp.common.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

//脑叶公司装备插槽
public enum LobeCorpEquipmentSlot implements StringRepresentable {
    LOBECORP_ANY(0, "lobecorp_any"),
    LOBECORP_WEAPON(1, "lobecorp_weapon"),
    LOBECORP_SUIT(2, "lobecorp_suit"),
    LOBECORP_BADGE(3, "lobecorp_badge"),
    LOBECORP_TOOL(4, "lobecorp_tool");

    public static final IntFunction<LobeCorpEquipmentSlot> BY_ID = ByIdMap.continuous(id -> id.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final Codec<LobeCorpEquipmentSlot> CODEC = StringRepresentable.fromEnum(LobeCorpEquipmentSlot::values);
    public static final StreamCodec<ByteBuf, LobeCorpEquipmentSlot> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, id -> id.id);
    private final int id;
    private final String key;

    LobeCorpEquipmentSlot(int id, String key) {
        this.id = id;
        this.key = key;
    }

    @Override
    public @NotNull String getSerializedName() {
        return key;
    }

    public String getSlotModifiersName() {
        return "item.modifiers." + getSerializedName();
    }
}
