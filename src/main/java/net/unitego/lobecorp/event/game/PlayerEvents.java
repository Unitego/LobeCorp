package net.unitego.lobecorp.event.game;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.network.payload.S2CSyncStatsPayload;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class PlayerEvents {
    //移除玩家数据
    @SubscribeEvent
    public static void removePlayerData(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            S2CSyncStatsPayload.HYDRATIONS.remove(serverPlayer.getUUID());
            S2CSyncStatsPayload.DESICCATIONS.remove(serverPlayer.getUUID());
            S2CSyncStatsPayload.SATURATIONS.remove(serverPlayer.getUUID());
            S2CSyncStatsPayload.EXHAUSTIONS.remove(serverPlayer.getUUID());
        }
    }
}
