package net.unitego.lobecorp.common.network.sender;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.client.screen.LobeCorpScreen;
import net.unitego.lobecorp.common.network.payload.C2SLobeCorpPayload;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class C2SLobeCorpSender {
    public static void send(double[] values) {
        Map<String, Double> map = new HashMap<>();

        for (int i = 0; i < values.length && i < LobeCorpScreen.ATTRIBUTES.length; i++) {
            if (!Double.isNaN(values[i])) {
                String key = Objects.requireNonNull(BuiltInRegistries.ATTRIBUTE.getKey(LobeCorpScreen.ATTRIBUTES[i])).getPath();
                map.put(key, values[i]);
            }
        }

        PacketDistributor.sendToServer(new C2SLobeCorpPayload(map));
    }
}
