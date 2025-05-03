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
        basicItem(ModItems.LOBE_CORP_LOGO.get());
        basicItem(ModItems.BLUE_LEAF.get());
    }
}
