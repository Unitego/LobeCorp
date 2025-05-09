package net.unitego.lobecorp.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.menu.EquipmentMenu;

public class MenusRegistry {
    public static final String CONTAINER_LOBECORP_EQUIPMENT = "container.lobecorp.equipment";

    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister
            .create(Registries.MENU, LobeCorp.MOD_ID);

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }

    public static final DeferredHolder<MenuType<?>, MenuType<EquipmentMenu>> EQUIPMENT_MENU = REGISTER.register("equipment_menu",
            () -> new MenuType<>(EquipmentMenu::new, FeatureFlags.VANILLA_SET));


}
