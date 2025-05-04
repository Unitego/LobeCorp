package net.unitego.lobecorp.common.network.handler;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.client.screen.LobeCorpScreen;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.StaffData;
import net.unitego.lobecorp.common.network.payload.C2SLobeCorpPayload;

import java.util.Map;
import java.util.Objects;

public class C2SLobeCorpHandler {
    public static void handle(C2SLobeCorpPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            StaffData staffData = ((DataAccess) player).lobeCorp$getStaffData();
            Map<String, Double> values = payload.values();
            for (int i = 0; i < LobeCorpScreen.ATTRIBUTES.length; i++) {
                String key = Objects.requireNonNull(BuiltInRegistries.ATTRIBUTE.getKey(LobeCorpScreen.ATTRIBUTES[i])).getPath();
                Double value = values.get(key);
                if (value != null) {
                    switch (i) {
                        case 0 -> staffData.setMaxHealth(value);
                        case 1 -> staffData.setMaxSanity(value);
                        case 2 -> staffData.setWorkSuccess(value);
                        case 3 -> staffData.setAttackVelocity(value);
                        case 4 -> staffData.setWorkVelocity(value);
                        case 5 -> staffData.setMoveVelocity(value);
                    }
                }
            }
        });
    }
}
