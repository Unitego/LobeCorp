package net.unitego.lobecorp.network.handler;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.client.gui.screen.SetAttributeScreen;
import net.unitego.lobecorp.network.payload.S2CUseLogoPayload;

public class S2CUseLogoHandler {
    public static void handle(S2CUseLogoPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> Minecraft.getInstance().setScreen(new SetAttributeScreen()));
    }
}
