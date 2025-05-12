package net.unitego.lobecorp.network.sender;

import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.network.payload.C2SSwitchWeaponPayload;

public class C2SSwitchWeaponSender {
    public static void send() {
        PacketDistributor.sendToServer(new C2SSwitchWeaponPayload());
    }
}
