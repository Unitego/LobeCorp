package net.unitego.lobecorp.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.unitego.lobecorp.LobeCorp;
import org.jetbrains.annotations.NotNull;

public record C2SSwitchWeaponPayload() implements CustomPacketPayload {
    public static final Type<C2SSwitchWeaponPayload> TYPE = new Type<>(LobeCorp.rl("switch_weapon"));

    public static final StreamCodec<FriendlyByteBuf, C2SSwitchWeaponPayload> STREAM_CODEC =
            StreamCodec.unit(new C2SSwitchWeaponPayload());

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
