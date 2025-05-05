package net.unitego.lobecorp.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.registry.ModItems;

public class ItemModelDataGen extends ItemModelProvider {
    public ItemModelDataGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LobeCorp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.LOBECORP_LOGO.get());
        basicItem(ModItems.BLUE_LEAF.get());
        basicItem(ModItems.CODE_SUIT.get());
        basicItem(ModItems.CODE_RIOT_STICK.get());
        basicItem(ModItems.STANDARD_SUIT.get());
        basicItem(ModItems.RED_RIOT_STICK.get());
        basicItem(ModItems.WHITE_RIOT_STICK.get());
        basicItem(ModItems.BLACK_RIOT_STICK.get());
        basicItem(ModItems.PALE_RIOT_STICK.get());
    }
}
