package net.unitego.lobecorp.common.network.sender;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.common.network.payload.S2CSwingHandPayload;

public class S2CSwingHandSender {
    public static void send(ServerPlayer serverPlayer) {
        serverPlayer.swing(serverPlayer.getUsedItemHand());
        PacketDistributor.sendToPlayer(serverPlayer, new S2CSwingHandPayload());
    }
}
