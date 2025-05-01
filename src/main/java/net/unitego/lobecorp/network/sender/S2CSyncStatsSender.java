package net.unitego.lobecorp.network.sender;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.access.DataAccess;
import net.unitego.lobecorp.data.SanityData;
import net.unitego.lobecorp.data.WaterData;
import net.unitego.lobecorp.network.payload.S2CSetSyncStatsPayload;

public class S2CSyncStatsSender {
    public static void send(ServerPlayer serverPlayer) {
        SanityData sanityData = ((DataAccess) serverPlayer).lobeCorp$getSanityData();
        WaterData waterData = ((DataAccess) serverPlayer).lobeCorp$getWaterData();
        FoodData foodData = serverPlayer.getFoodData();

        float sanity = sanityData.getSanity();
        int waterLevel = waterData.getWaterLevel();
        float hydrationLevel = waterData.getHydrationLevel();
        float desiccationLevel = waterData.getDesiccationLevel();
        float exhaustionLevel = foodData.getExhaustionLevel();

        PacketDistributor.sendToPlayer(serverPlayer, new S2CSetSyncStatsPayload(
                sanity,
                waterLevel, hydrationLevel,
                desiccationLevel, exhaustionLevel));
    }
}
