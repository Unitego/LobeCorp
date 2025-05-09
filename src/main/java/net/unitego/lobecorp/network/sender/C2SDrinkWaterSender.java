package net.unitego.lobecorp.network.sender;

import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.network.payload.C2SDrinkWaterPayload;

public class C2SDrinkWaterSender {
    public static void send(LocalPlayer localPlayer, String waterSource) {
        localPlayer.swing(localPlayer.getUsedItemHand());
        PacketDistributor.sendToServer(new C2SDrinkWaterPayload(waterSource));
    }
}
