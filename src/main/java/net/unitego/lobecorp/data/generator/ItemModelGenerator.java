package net.unitego.lobecorp.data.generator;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.registry.ItemsRegistry;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LobeCorp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ItemsRegistry.LOBECORP_LOGO.get());
        basicItem(ItemsRegistry.BLUE_LEAF.get());
        basicItem(ItemsRegistry.CODE_SUIT.get());
        basicItem(ItemsRegistry.CODE_RIOT_STICK.get());
        basicItem(ItemsRegistry.STANDARD_SUIT.get());
        basicItem(ItemsRegistry.RED_RIOT_STICK.get());
        basicItem(ItemsRegistry.WHITE_RIOT_STICK.get());
        basicItem(ItemsRegistry.BLACK_RIOT_STICK.get());
        basicItem(ItemsRegistry.PALE_RIOT_STICK.get());
    }
}
