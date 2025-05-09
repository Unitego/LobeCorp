package net.unitego.lobecorp.network.sender;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.SanityManager;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.network.payload.S2CSetSanityPayload;

public class S2CSetSanitySender {
    public static void send(ServerPlayer serverPlayer) {
        SanityManager sanityManager = ((ManagerAccess) serverPlayer).lobeCorp$getSanityManager();
        WaterManager waterManager = ((ManagerAccess) serverPlayer).lobeCorp$getWaterManager();

        float sanity = sanityManager.getSanity();
        int waterLevel = waterManager.getWaterLevel();
        float hydrationLevel = waterManager.getHydrationLevel();

        PacketDistributor.sendToPlayer(serverPlayer, new S2CSetSanityPayload(
                sanity, waterLevel, hydrationLevel
        ));
    }
}
