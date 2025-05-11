package net.unitego.lobecorp.network.handler;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.client.gui.screen.SetAttributeScreen;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.network.payload.C2SSetAttributePayload;

import java.util.Map;
import java.util.Objects;

public class C2SSetAttributeHandler {
    public static void handle(C2SSetAttributePayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            StaffManager staffManager = ((ManagerAccess) player).lobeCorp$getStaffManager();
            Map<String, Double> values = payload.values();
            for (int i = 0; i < SetAttributeScreen.ATTRIBUTES.length; i++) {
                String key = Objects.requireNonNull(BuiltInRegistries.ATTRIBUTE.getKey(SetAttributeScreen.ATTRIBUTES[i])).getPath();
                Double value = values.get(key);
                if (value != null) {
                    switch (i) {
                        case 0 -> staffManager.setMaxHealthBaseValue(value);
                        case 1 -> staffManager.setMaxSanityBaseValue(value);
                        case 2 -> staffManager.setWorkSuccessBaseValue(value);
                        case 3 -> staffManager.setAttackVelocityBaseValue(value);
                        case 4 -> staffManager.setWorkVelocityBaseValue(value);
                        case 5 -> staffManager.setMoveVelocityBaseValue(value);
                    }
                }
            }
        });
    }
}
