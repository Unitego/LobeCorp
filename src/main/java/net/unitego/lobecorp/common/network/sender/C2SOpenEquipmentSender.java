package net.unitego.lobecorp.common.network.sender;

import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.common.network.payload.C2SOpenEquipmentPayload;

public class C2SOpenEquipmentSender {
    public static void send() {
        PacketDistributor.sendToServer(new C2SOpenEquipmentPayload());
    }
}
