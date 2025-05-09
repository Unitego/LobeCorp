package net.unitego.lobecorp.network.sender;

import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.network.payload.C2SOpenEquipmentPayload;

public class C2SOpenEquipmentSender {
    public static void send() {
        PacketDistributor.sendToServer(new C2SOpenEquipmentPayload());
    }
}
