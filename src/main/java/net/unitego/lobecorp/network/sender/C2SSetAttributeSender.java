package net.unitego.lobecorp.network.sender;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.client.gui.screen.SetAttributeScreen;
import net.unitego.lobecorp.network.payload.C2SSetAttributePayload;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class C2SSetAttributeSender {
    public static void send(double[] values) {
        Map<String, Double> map = new HashMap<>();

        for (int i = 0; i < values.length && i < SetAttributeScreen.ATTRIBUTES.length; i++) {
            if (!Double.isNaN(values[i])) {
                String key = Objects.requireNonNull(BuiltInRegistries.ATTRIBUTE.getKey(SetAttributeScreen.ATTRIBUTES[i])).getPath();
                map.put(key, values[i]);
            }
        }

        PacketDistributor.sendToServer(new C2SSetAttributePayload(map));
    }
}
