package net.unitego.lobecorp.client.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.unitego.lobecorp.client.gui.screen.EquipmentScreen;
import net.unitego.lobecorp.registry.MenusRegistry;

public class ScreenInit {
    public static void init(RegisterMenuScreensEvent event) {
        event.register(MenusRegistry.EQUIPMENT_MENU.get(), EquipmentScreen::new);
    }
}
