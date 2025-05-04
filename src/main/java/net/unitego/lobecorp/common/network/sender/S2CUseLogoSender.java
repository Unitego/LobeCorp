package net.unitego.lobecorp.common.network.sender;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.common.network.payload.S2CUseLogoPayload;

public class S2CUseLogoSender {
    public static void send(ServerPlayer serverPlayer) {
        PacketDistributor.sendToPlayer(serverPlayer, new S2CUseLogoPayload());
    }
}
