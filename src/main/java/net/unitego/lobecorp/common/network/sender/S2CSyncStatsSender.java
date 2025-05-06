package net.unitego.lobecorp.common.network.sender;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.network.payload.S2CSyncStatsPayload;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class S2CSyncStatsSender {
    private static final Map<UUID, Float> HYDRATIONS = new HashMap<>();
    private static final Map<UUID, Float> DESICCATIONS = new HashMap<>();
    private static final Map<UUID, Float> SATURATIONS = new HashMap<>();
    private static final Map<UUID, Float> EXHAUSTIONS = new HashMap<>();

    public static void send(ServerPlayer serverPlayer) {
        Float hydration = HYDRATIONS.get(serverPlayer.getUUID());
        float hydrationLevel = ((DataAccess) serverPlayer).lobeCorp$getWaterData().getHydrationLevel();
        if (hydration == null || hydration != hydrationLevel) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncStatsPayload(S2CSyncStatsPayload.HYDRATION, hydrationLevel));
            HYDRATIONS.put(serverPlayer.getUUID(), hydrationLevel);
        }
        Float desiccation = DESICCATIONS.get(serverPlayer.getUUID());
        float desiccationLevel = ((DataAccess) serverPlayer).lobeCorp$getWaterData().getDesiccationLevel();
        if (desiccation == null || Mth.abs(desiccation - desiccationLevel) >= 0.01F) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncStatsPayload(S2CSyncStatsPayload.DESICCATION, desiccationLevel));
            DESICCATIONS.put(serverPlayer.getUUID(), desiccationLevel);
        }
        Float saturation = SATURATIONS.get(serverPlayer.getUUID());
        float saturationLevel = serverPlayer.getFoodData().getSaturationLevel();
        if (saturation == null || saturation != saturationLevel) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncStatsPayload(S2CSyncStatsPayload.SATURATION, saturationLevel));
            SATURATIONS.put(serverPlayer.getUUID(), saturationLevel);
        }
        Float exhaustion = EXHAUSTIONS.get(serverPlayer.getUUID());
        float exhaustionLevel = serverPlayer.getFoodData().getExhaustionLevel();
        if (exhaustion == null || Mth.abs(exhaustion - exhaustionLevel) >= 0.01F) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncStatsPayload(S2CSyncStatsPayload.EXHAUSTION, exhaustionLevel));
            EXHAUSTIONS.put(serverPlayer.getUUID(), exhaustionLevel);
        }
    }

    public static void remove(ServerPlayer serverPlayer) {
        HYDRATIONS.remove(serverPlayer.getUUID());
        DESICCATIONS.remove(serverPlayer.getUUID());
        SATURATIONS.remove(serverPlayer.getUUID());
        EXHAUSTIONS.remove(serverPlayer.getUUID());
    }
}
