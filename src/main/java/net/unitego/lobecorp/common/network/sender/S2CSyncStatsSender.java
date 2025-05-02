package net.unitego.lobecorp.common.network.sender;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.network.payload.S2CSyncStatsPayload;

public class S2CSyncStatsSender {
    public static void send(ServerPlayer serverPlayer) {
        Float hydration = S2CSyncStatsPayload.HYDRATIONS.get(serverPlayer.getUUID());
        float hydrationLevel = ((DataAccess) serverPlayer).lobeCorp$getWaterData().getHydrationLevel();
        if (hydration == null || hydration != hydrationLevel) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncStatsPayload(S2CSyncStatsPayload.HYDRATION, hydrationLevel));
            S2CSyncStatsPayload.HYDRATIONS.put(serverPlayer.getUUID(), hydrationLevel);
        }
        Float desiccation = S2CSyncStatsPayload.DESICCATIONS.get(serverPlayer.getUUID());
        float desiccationLevel = ((DataAccess) serverPlayer).lobeCorp$getWaterData().getDesiccationLevel();
        if (desiccation == null || Mth.abs(desiccation - desiccationLevel) >= 0.01F) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncStatsPayload(S2CSyncStatsPayload.DESICCATION, desiccationLevel));
            S2CSyncStatsPayload.DESICCATIONS.put(serverPlayer.getUUID(), desiccationLevel);
        }
        Float saturation = S2CSyncStatsPayload.SATURATIONS.get(serverPlayer.getUUID());
        float saturationLevel = serverPlayer.getFoodData().getSaturationLevel();
        if (saturation == null || saturation != saturationLevel) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncStatsPayload(S2CSyncStatsPayload.SATURATION, saturationLevel));
            S2CSyncStatsPayload.SATURATIONS.put(serverPlayer.getUUID(), saturationLevel);
        }
        Float exhaustion = S2CSyncStatsPayload.EXHAUSTIONS.get(serverPlayer.getUUID());
        float exhaustionLevel = serverPlayer.getFoodData().getExhaustionLevel();
        if (exhaustion == null || Mth.abs(exhaustion - exhaustionLevel) >= 0.01F) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncStatsPayload(S2CSyncStatsPayload.EXHAUSTION, exhaustionLevel));
            S2CSyncStatsPayload.EXHAUSTIONS.put(serverPlayer.getUUID(), exhaustionLevel);
        }
    }
}
