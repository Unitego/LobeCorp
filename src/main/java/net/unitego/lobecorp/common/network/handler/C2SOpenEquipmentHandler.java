package net.unitego.lobecorp.common.network.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.menu.EquipmentMenu;
import net.unitego.lobecorp.common.network.payload.C2SOpenEquipmentPayload;
import net.unitego.lobecorp.common.registry.ModMenus;

public class C2SOpenEquipmentHandler {
    public static void handle(C2SOpenEquipmentPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            context.player().openMenu(new SimpleMenuProvider(
                    (id, inventory, player) -> new EquipmentMenu(id, inventory),
                    Component.translatable(ModMenus.CONTAINER_LOBECORP_EQUIPMENT)
            ));
        });
    }
}
