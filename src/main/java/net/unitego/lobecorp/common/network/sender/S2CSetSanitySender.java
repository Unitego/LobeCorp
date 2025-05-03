package net.unitego.lobecorp.common.network.sender;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.payload.S2CSetSanityPayload;

public class S2CSetSanitySender {
    public static void send(ServerPlayer serverPlayer) {
        SanityData sanityData = ((DataAccess) serverPlayer).lobeCorp$getSanityData();
        WaterData waterData = ((DataAccess) serverPlayer).lobeCorp$getWaterData();

        float sanity = sanityData.getSanity();
        int waterLevel = waterData.getWaterLevel();
        float hydrationLevel = waterData.getHydrationLevel();

        PacketDistributor.sendToPlayer(serverPlayer, new S2CSetSanityPayload(
                sanity, waterLevel, hydrationLevel
        ));
    }
}
