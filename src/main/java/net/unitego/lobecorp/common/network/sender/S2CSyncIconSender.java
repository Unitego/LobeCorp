package net.unitego.lobecorp.common.network.sender;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.network.payload.S2CSyncIconPayload;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class S2CSyncIconSender {
    private static final Map<UUID, Float> HYDRATIONS = new HashMap<>();
    private static final Map<UUID, Float> DESICCATIONS = new HashMap<>();
    private static final Map<UUID, Float> SATURATIONS = new HashMap<>();
    private static final Map<UUID, Float> EXHAUSTIONS = new HashMap<>();

    public static void send(ServerPlayer serverPlayer) {
        Float hydration = HYDRATIONS.get(serverPlayer.getUUID());
        float hydrationLevel = ((DataAccess) serverPlayer).lobeCorp$getWaterData().getHydrationLevel();
        if (hydration == null || hydration != hydrationLevel) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncIconPayload(S2CSyncIconPayload.HYDRATION, hydrationLevel));
            HYDRATIONS.put(serverPlayer.getUUID(), hydrationLevel);
        }
        Float desiccation = DESICCATIONS.get(serverPlayer.getUUID());
        float desiccationLevel = ((DataAccess) serverPlayer).lobeCorp$getWaterData().getDesiccationLevel();
        if (desiccation == null || Math.abs(desiccation - desiccationLevel) >= 0.01F) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncIconPayload(S2CSyncIconPayload.DESICCATION, desiccationLevel));
            DESICCATIONS.put(serverPlayer.getUUID(), desiccationLevel);
        }
        Float saturation = SATURATIONS.get(serverPlayer.getUUID());
        float saturationLevel = serverPlayer.getFoodData().getSaturationLevel();
        if (saturation == null || saturation != saturationLevel) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncIconPayload(S2CSyncIconPayload.SATURATION, saturationLevel));
            SATURATIONS.put(serverPlayer.getUUID(), saturationLevel);
        }
        Float exhaustion = EXHAUSTIONS.get(serverPlayer.getUUID());
        float exhaustionLevel = serverPlayer.getFoodData().getExhaustionLevel();
        if (exhaustion == null || Math.abs(exhaustion - exhaustionLevel) >= 0.01F) {
            PacketDistributor.sendToPlayer(serverPlayer, new S2CSyncIconPayload(S2CSyncIconPayload.EXHAUSTION, exhaustionLevel));
            EXHAUSTIONS.put(serverPlayer.getUUID(), exhaustionLevel);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            HYDRATIONS.remove(serverPlayer.getUUID());
            DESICCATIONS.remove(serverPlayer.getUUID());
            SATURATIONS.remove(serverPlayer.getUUID());
            EXHAUSTIONS.remove(serverPlayer.getUUID());
        }
    }
}
