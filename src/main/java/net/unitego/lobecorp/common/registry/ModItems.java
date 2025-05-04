package net.unitego.lobecorp.common.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.item.LobeCorpLogo;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LobeCorp.MOD_ID);

    public static final DeferredItem<LobeCorpLogo> LOBE_CORP_LOGO = ITEMS.register("lobe_corp_logo", LobeCorpLogo::new);
    public static final DeferredItem<Item> BLUE_LEAF = register("blue_leaf",
            new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).fast().build()));

    public static DeferredItem<Item> register(String name, Item.Properties properties) {
        return ITEMS.registerSimpleItem(name, properties);
    }
}
