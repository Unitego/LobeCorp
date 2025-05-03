package net.unitego.lobecorp.common.network.sender;

import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.common.network.payload.C2SDrinkWaterPayload;

public class C2SDrinkWaterSender {
    public static void send(String waterSource) {
        PacketDistributor.sendToServer(new C2SDrinkWaterPayload(waterSource));
    }
}
