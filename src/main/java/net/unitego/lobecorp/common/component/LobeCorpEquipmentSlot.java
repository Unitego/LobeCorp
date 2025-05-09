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
    LOBECORP_WEAPON(1, "lobecorp_weapon"),//武器
    LOBECORP_SUIT(2, "lobecorp_suit"),//护甲
    LOBECORP_BADGE(3, "lobecorp_badge"),//袖标
    LOBECORP_TOOL(4, "lobecorp_tool"),//工具
    LOBECORP_HAT(5, "lobecorp_hat"),//头饰
    LOBECORP_HEAD(6, "lobecorp_head"),//头部
    LOBECORP_OCCIPUT(7, "lobecorp_occiput"),//后脑
    LOBECORP_EYE(8, "lobecorp_eye"),//眼部
    LOBECORP_FACE(9, "lobecorp_face"),//面部
    LOBECORP_CHEEK(10, "lobecorp_cheek"),//脸颊
    LOBECORP_MASK(11, "lobecorp_mask"),//面具
    LOBECORP_MOUTH(12, "lobecorp_mouth"),//口部
    LOBECORP_NECK(13, "lobecorp_neck"),//颈部
    LOBECORP_CHEST(14, "lobecorp_chest"),//胸部
    LOBECORP_HAND(15, "lobecorp_hand"),//手部
    LOBECORP_GLOVE(16, "lobecorp_glove"),//手套
    LOBECORP_RIGHTBACK(17, "lobecorp_rightback"),//右背
    LOBECORP_LEFTBACK(18, "lobecorp_leftback");//左背

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
